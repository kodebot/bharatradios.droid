package uk.co.qubitssolutions.bharatradios.services.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;

import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.model.Constants;

/**
 * JellyBeanAudioPlayer used to play audio. This uses Exo player from google
 * which is available from API level 16 (Jelly Bean);
 */
public class JellyBeanAudioPlayer extends AudioPlayer {
    private static final int SEGMENT_SIZE = 1024;
    private static final int SEGMENT_COUNT = 32;
    private static final int RENDERER_COUNT = 2;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";

    private StateChangeListener stateChangeListener;
    private Context context;
    private ExoPlayer exoPlayer;
    private ExoPlayer.Listener exoPlayerListener;
    private MediaCodecAudioTrackRenderer audioTrackRenderer;
    private Visualizer visualizer;

    public JellyBeanAudioPlayer(Context context, StateChangeListener stateChangeListener) {
        if (context == null)
            throw new IllegalArgumentException("Context is not optional, the value supplied must not be null.");

        this.stateChangeListener = stateChangeListener;
        this.context = context;
    }

    @Override
    public void initPlayer() {
        Log.v(Constants.LOG_TAG, "Initialising player");
        exoPlayer = ExoPlayer.Factory.newInstance(RENDERER_COUNT, 0, 0);
        addStateChangeEventListeners();

        Log.v(Constants.LOG_TAG, "Player initialised successfully");
    }

    @Override
    public void releasePlayer() {
        if (visualizer != null) {
            visualizer.release();
        }
        if (exoPlayer == null) return;
        Log.v(Constants.LOG_TAG, "Releasing player");
        exoPlayer.release();
        exoPlayer.removeListener(exoPlayerListener);
        exoPlayer = null;
        Log.v(Constants.LOG_TAG, "Player released successfully");
    }

    @Override
    public void play(String url) {
        if (exoPlayer == null)
            throw new IllegalStateException("Player must be initialised to play.");

        Log.v(Constants.LOG_TAG, "Player going to play " + url);
        Allocator allocator = new DefaultAllocator(SEGMENT_SIZE);
        DataSource dataSource = new DefaultUriDataSource(context, USER_AGENT);
        Mp3Extractor mp3Extractor = new Mp3Extractor();
        ExtractorSampleSource sampleSource = new ExtractorSampleSource(
                Uri.parse(url),
                dataSource,
                allocator,
                SEGMENT_COUNT * SEGMENT_SIZE, mp3Extractor);
        audioTrackRenderer = new MediaCodecAudioTrackRenderer(
                sampleSource, MediaCodecSelector.DEFAULT, null, false, null, null, null, AudioManager.STREAM_MUSIC) {
            @Override
            protected void onAudioSessionId(int audioSessionId) {
                super.onAudioSessionId(audioSessionId);

                if (((BharatRadiosApplication) context).hasRecordAudioPermisssion()) {

                    if (visualizer != null) {
                        visualizer.release();
                    }
                    visualizer = new Visualizer(audioSessionId);
                    visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
                    visualizer.setDataCaptureListener(
                            new Visualizer.OnDataCaptureListener() {
                                public void onWaveFormDataCapture(Visualizer visualizer,
                                                                  byte[] bytes, int samplingRate) {
                                    ((BharatRadiosApplication) context).audioVisualizerData.onNext(bytes);
                                }

                                public void onFftDataCapture(Visualizer visualizer,
                                                             byte[] bytes, int samplingRate) {
                                }
                            }, Visualizer.getMaxCaptureRate() / 4, true, false);
                    visualizer.setEnabled(true);
                }
            }
        };

        exoPlayer.prepare(audioTrackRenderer);
        exoPlayer.setPlayWhenReady(true);
        Log.v(Constants.LOG_TAG, "Player playing " + url);
    }

    @Override
    public void stop() {
        if (exoPlayer == null)
            throw new IllegalStateException("Player must be initialised to stop.");
        Log.v(Constants.LOG_TAG, "Player stopping");
        exoPlayer.stop();
        Log.v(Constants.LOG_TAG, "Player stopped");
    }

    @Override
    public void setVolume(float volume) {
        if (exoPlayer == null)
            throw new IllegalStateException("Player must be initialised to set volume.");
        if (audioTrackRenderer == null)
            return; // quietly return when audio track renderer is not initialised.
        Log.v(Constants.LOG_TAG, "Setting volume to " + volume);
        exoPlayer.sendMessage(audioTrackRenderer, MediaCodecAudioTrackRenderer.MSG_SET_VOLUME, volume);
    }

    /******************************************************************************************************************
     * ***********************************Private methods **************************************************************
     ******************************************************************************************************************/
    private void addStateChangeEventListeners() {
        if (stateChangeListener != null) {
            exoPlayerListener = new ExoPlayer.Listener() {

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    switch (playbackState) {
                        case ExoPlayer.STATE_BUFFERING:
                            Log.v(Constants.LOG_TAG, "Player state buffering");
                            stateChangeListener.onBuffering(0); // todo: force restart if buffering take more than 5 sec
                            break;
                        case ExoPlayer.STATE_ENDED:
                            Log.v(Constants.LOG_TAG, "Player state ended");
                            stateChangeListener.onComplete();
                            break;
                        case ExoPlayer.STATE_PREPARING:
                            Log.v(Constants.LOG_TAG, "Player state preparing");
                            stateChangeListener.onBuffering(0); // todo: get the current buffering percentage and pass
                            break;
                        case ExoPlayer.STATE_READY:
                            Log.v(Constants.LOG_TAG, "Player state ready");
                            stateChangeListener.onPlaying();
                            break;
                        case ExoPlayer.STATE_IDLE:
                            Log.v(Constants.LOG_TAG, "Player state idle");
                            stateChangeListener.onStopped();
                            break;
                    }
                }

                @Override
                public void onPlayWhenReadyCommitted() {
                    // do nothing
                    Log.v(Constants.LOG_TAG, "Play when ready committed");
                }

                @Override
                public void onPlayerError(ExoPlaybackException e) {
                    stateChangeListener.onError(e);
                    Log.e(Constants.LOG_TAG, "Error while playing", e);
                }
            };

            exoPlayer.addListener(exoPlayerListener);
        }
    }
}
