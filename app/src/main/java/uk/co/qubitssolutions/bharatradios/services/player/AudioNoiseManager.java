package uk.co.qubitssolutions.bharatradios.services.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import uk.co.qubitssolutions.bharatradios.app.activities.MainActivity;
import uk.co.qubitssolutions.bharatradios.app.services.BackgroundAudioPlayerService;
import uk.co.qubitssolutions.bharatradios.model.Constants;


public class AudioNoiseManager extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY) &&
                MainActivity.class != null) {
            Intent stopPlayerIntent = new Intent(context, BackgroundAudioPlayerService.class);
            stopPlayerIntent.putExtra(Constants.EXTRA_ACTION, Constants.ACTION_STOP);
            context.startService(stopPlayerIntent);
        }
    }
}
