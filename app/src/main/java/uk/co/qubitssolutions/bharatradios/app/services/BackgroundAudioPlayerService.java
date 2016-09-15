package uk.co.qubitssolutions.bharatradios.app.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import rx.functions.Action1;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.activities.MainActivity;
import uk.co.qubitssolutions.bharatradios.model.Constants;
import uk.co.qubitssolutions.bharatradios.model.PlayerStatusType;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.model.Stream;
import uk.co.qubitssolutions.bharatradios.services.data.radio.CurrentlyPlayingInfoHtmlScrapper;
import uk.co.qubitssolutions.bharatradios.services.data.radio.PlsParser;
import uk.co.qubitssolutions.bharatradios.services.data.radio.ShoutcastDataReader;
import uk.co.qubitssolutions.bharatradios.services.player.AudioPlayer;
import uk.co.qubitssolutions.bharatradios.services.player.MediaSessionCallback;
import uk.co.qubitssolutions.bharatradios.services.player.RemoteControlReceiver;

public class BackgroundAudioPlayerService extends Service
        implements OnAudioFocusChangeListener,
        AudioPlayer.StateChangeListener {

    // todo : detect wifi connection and stop playing if preference is set to play only on wifi -- add preference
    // todo : hardware button integration
    private static WifiManager.WifiLock wifiLock;
    private static PowerManager.WakeLock wakeLock;
    private static String currentlyPlayingUrl;
    private static Timer stopTimer;
    private static AudioPlayer audioPlayer;
    private static boolean isTransientAudioFocusLoss = false;
    private static boolean isDucked = false;
    private static String currentRadioName;

    private static final float DUCKING_VOLUME = 0.1f;
    private static final float FULL_VOLUME = 1.0f;

    public static MediaSessionCompat mediaSession;

    public final int NOTIFICATION_ID = 12756;
    private BharatRadiosApplication application;

    public BackgroundAudioPlayerService() {
        super();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(Constants.LOG_TAG, "Destroying the background service");
        stopForeground(true);
        actionStop();
        teardownPlayer();

        // reset states
        isTransientAudioFocusLoss = false;
        isDucked = false;
        // todo: change this as callback
        // application.getRadioData().setIsCurrentlyPlaying(false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(Constants.LOG_TAG, "Creating the background service");
        application = (BharatRadiosApplication) getApplication();
        audioPlayer = AudioPlayer.getInstance(getApplicationContext(), this);
        setupPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null)
            return super.onStartCommand(null, flags, startId); // just call super when intent is null
        Log.v(Constants.LOG_TAG, "Starting command");
        Runnable task = createIntentTask(intent);
        new Thread(task).start();
        return START_STICKY;
    }

    private Runnable createIntentTask(final Intent intent) {
        return new Runnable() {
            @Override
            public void run() {
                handleIntent(intent);
            }
        };
    }

    private void handleIntent(Intent intent) {

        Log.v(Constants.LOG_TAG, "Handling intent");

        String action = intent.getStringExtra(Constants.EXTRA_ACTION);
        try {
            Log.v(Constants.LOG_TAG, "Processing run: " + action);
            switch (action) {
                case Constants.ACTION_PLAY:
                    currentlyPlayingUrl = resolveShoutcastUrl(application.getCurrentRadio().getStreams());
                    currentRadioName = application.getCurrentRadio().getName();
                    actionPlay();
                    setupAsForeground();
                    break;
                case Constants.ACTION_STOP:
                    actionStop();
                    stopForeground(true);
                    break;
                case Constants.ACTION_SCHEDULE_CLOSE:
                    actionScheduleClose();
                    break;
                case Constants.ACTION_CANCEL_SCHEDULED_CLOSE:
                    actionCancelScheduledClose();
                    break;
            }
        } catch (Exception ex) {
            // TODO: change the radio status
            Log.e(Constants.LOG_TAG, "Error when handling the intent...", ex);
        }
    }

    /*******************************************************************************************************************
     * ************************************Intent run methods ********************************************************
     ******************************************************************************************************************/
    private void actionPlay() {
        if (currentlyPlayingUrl != null) {
            Log.v(Constants.LOG_TAG, "Playing beginning");
            audioPlayer.play(currentlyPlayingUrl);
            Log.v(Constants.LOG_TAG, "Playing begun successfully");
            application.updateCurrentlyPlaying(CurrentlyPlayingInfoHtmlScrapper.getCurrentlyPlaying(currentlyPlayingUrl));
        }
    }

    private void actionStop() {
        Log.v(Constants.LOG_TAG, "Stopping...");
        audioPlayer.stop();
        Log.v(Constants.LOG_TAG, "Stopped successfully");
    }

    private void actionSetVolume() {
        Log.v(Constants.LOG_TAG, "Adjusting volume...");
        audioPlayer.setVolume(FULL_VOLUME);
        Log.v(Constants.LOG_TAG, "Volume adjusted successfully");
    }

    private void actionScheduleClose() {
        // // TODO: 24/07/2016
//        actionCancelScheduledClose(); // cancel any previously scheduled close
//        long closeTimeInMillis = application.getRadioData().getCloseTime().getTime();
//        long currentTimeInMillis = Calendar.getInstance().get(Calendar.MILLISECOND);
//        long durationInMillis = closeTimeInMillis - currentTimeInMillis;
//        stopTimer = new Timer();
//        stopTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                actionStop();
//                application.getRadioData().setCloseTime(null);
//                stopForeground(true);
//                fireActionCallback();
//            }
//        }, durationInMillis);
    }

    private void actionCancelScheduledClose() {
        if (stopTimer != null) {
            stopTimer.cancel();
        }
    }

    /*******************************************************************************************************************
     * ***************************************Internal private methods***************************************************
     ******************************************************************************************************************/
    private void setupPlayer() {
        Log.v(Constants.LOG_TAG, "Setting up the player...");
        audioPlayer.initPlayer();
        acquireWakeLock();
        acquireWifiLock();
        setupAudioFocus();
        setupMediaSession();
        Log.v(Constants.LOG_TAG, "Player setup successfully");

    }

    private void teardownPlayer() {
        Log.v(Constants.LOG_TAG, "Tearing down the player");
        if (audioPlayer == null) return;
        audioPlayer.releasePlayer();
        audioPlayer = null;
        releaseAudioFocus();
        mediaSession.setActive(false);
        releaseWakeLock();
        releaseWifiLock();

    }

    private void setupMediaSession() {
        ComponentName remoteControlReceiver = new ComponentName(
                getPackageName(),
                RemoteControlReceiver.class.getName());
        mediaSession = new MediaSessionCompat(
                getApplicationContext(),
                Constants.LOG_TAG,
                remoteControlReceiver, null);
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setCallback(new MediaSessionCallback());

        PlaybackStateCompat state = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_STOP)
                .setState(PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, SystemClock.elapsedRealtime())
                .build();

        mediaSession.setPlaybackState(state);
        mediaSession.setActive(true);
    }

    private void setupAudioFocus() {
        Log.v(Constants.LOG_TAG, "Trying to gain stream music audio focus.");
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.v(Constants.LOG_TAG, "Unable to gain audio focus");
        } else {
            Log.v(Constants.LOG_TAG, "Gained stream music audio focus successfully.");
        }
    }

    private void releaseAudioFocus() {
        Log.v(Constants.LOG_TAG, "Abandoning audio focus...");
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.abandonAudioFocus(this);
    }

    private void streamDuck() {
        Log.v(Constants.LOG_TAG, "Ducking audio...");
        audioPlayer.setVolume(DUCKING_VOLUME);
        isDucked = true;
        Log.v(Constants.LOG_TAG, "Ducking completed successfully");
    }

    private void acquireWakeLock() {
        Log.v(Constants.LOG_TAG, "Acquiring partial wake lock");
        if (wakeLock == null) {
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Constants.LOG_TAG);
            wakeLock.acquire();
        } else if (!wakeLock.isHeld()) {
            wakeLock.acquire();
        }
        Log.v(Constants.LOG_TAG, "Partial wake lock acquired");
    }

    private void releaseWakeLock() {
        if (wakeLock == null) return;
        Log.v(Constants.LOG_TAG, "Releasing partial wake lock");
        wakeLock.release();

        Log.v(Constants.LOG_TAG, "Partial wake lock released successfully");
    }

    private void acquireWifiLock() {
        Log.v(Constants.LOG_TAG, "Acquiring wifi lock");
        if (wifiLock == null) {
            wifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE))
                    .createWifiLock(WifiManager.WIFI_MODE_FULL, Constants.LOG_TAG);
            wifiLock.acquire();
        } else if (!wifiLock.isHeld()) {
            wifiLock.acquire();
        }
        Log.v(Constants.LOG_TAG, "Wifi lock acquired");
    }

    private void releaseWifiLock() {
        Log.v(Constants.LOG_TAG, "Releasing wifi lock");
        if (wifiLock != null && wifiLock.isHeld()) {
            wifiLock.release();
        }
        Log.v(Constants.LOG_TAG, "Wifi lock released");
    }

    private void setupAsForeground() {
        String contentText = "Tap to open";
        PendingIntent pi = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        int largeIconId = getApplicationContext().getResources()
                .getIdentifier("icon", "drawable", getApplicationContext().getPackageName());
        Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), largeIconId);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(android.R.drawable.ic_media_play)
                .setLargeIcon(largeIcon)
                .setContentTitle("Playing " + currentRadioName)
                .setContentText(contentText)
                .setContentIntent(pi);

        startForeground(NOTIFICATION_ID, builder.build());
    }

    /*********************************************************************
     * *****************AudioFocusChangeListener methods*******************
     *********************************************************************/
    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.v(Constants.LOG_TAG, "Audio focus gained");
                if (isTransientAudioFocusLoss) {
                    Log.v(Constants.LOG_TAG, "Recovering from transient audio focus loss");
                    actionPlay();
                    isTransientAudioFocusLoss = false;
                    Log.v(Constants.LOG_TAG, "Recovered from transient audio focus loss");
                }

                if (isDucked) {
                    Log.v(Constants.LOG_TAG, "Recovering from duck'able transient audio focus loss");
                    actionSetVolume();
                    isDucked = false;
                    Log.v(Constants.LOG_TAG, "Recovered from duck'able transient audio focus loss");
                }
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                Log.v(Constants.LOG_TAG, "Audio focus lost");
                actionStop();
                stopForeground(true);
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.v(Constants.LOG_TAG, "Audio focus transient lost");
                if (application.isPlaying()) {
                    isTransientAudioFocusLoss = true;
                }
                actionStop();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                Log.v(Constants.LOG_TAG, "Audio focus loss transient can duck");
                if (application.isPlaying()) {
                    streamDuck();
                }
                break;
        }
    }

    /*********************************************************************
     * *****************StageChangeListener methods*******************
     *********************************************************************/
    @Override
    public void onPlaying() {
        Log.v(Constants.LOG_TAG, "Playing event fired");
        application.setPlayerStatus(PlayerStatusType.PLAYING);
    }

    @Override
    public void onStopped() {
        Log.v(Constants.LOG_TAG, "Stopped event fired");
        // TODO: 24/07/2016 use callback
        application.setPlayerStatus(PlayerStatusType.STOPPED);
    }

    @Override
    public void onBuffering(int percent) {
        Log.v(Constants.LOG_TAG, "Buffering event fired " + percent);
        application.setPlayerStatus(PlayerStatusType.BUFFERING);
    }

    @Override
    public void onComplete() {
        Log.v(Constants.LOG_TAG, "Completed event fired");
    }

    @Override
    public void onError(Exception ex) {
        Log.e(Constants.LOG_TAG, "Error reported", ex);
        application.setPlayerStatus(PlayerStatusType.ERROR);
    }

    private String resolveShoutcastUrl(ArrayList<Stream> streams) {
        PlsParser parser = new PlsParser();
        for (final Stream stream : streams) {
            if (stream.getSrc().equalsIgnoreCase("http://www.shoutcast.com/")) {
                String shoutcastTuneInUrl = stream.getUrl();
                List<ShoutcastDataReader.ShoutcastStation> stations =
                        ShoutcastDataReader.searchStation(stream.getSrcName(), stream.getBitRate());
                if (!stations.isEmpty()) {
                    String plsUrl = shoutcastTuneInUrl.replaceAll("<base>", stations.get(0).base)
                            .replaceAll("<id>", stations.get(0).id);

                    String url = parser.getUrls(plsUrl).get(0);
                    url = url + "/;?icy=http";
                    stream.setUrl(url);

                    // this is to ensure the updated url is not resolved as shoutcast stream again
                    // when user click the same radio again
                    stream.setSrc("Direct");
                    application.setCurrentStream(stream);
                    return url;
                }

            } else {
                application.setCurrentStream(stream);
                return stream.getUrl();
            }
        }

        return null;
    }

}
