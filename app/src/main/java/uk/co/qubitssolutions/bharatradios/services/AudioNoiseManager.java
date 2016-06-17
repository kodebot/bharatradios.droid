package uk.co.qubitssolutions.bharatradios.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;



public class AudioNoiseManager extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY) &&
//                HomeActivity.class != null) {
//            Intent stopPlayerIntent = new Intent(
//                    BackgroundAudioPlayerPlugin.mainActivity.getApplicationContext(),
//                    BackgroundAudioPlayerService.class);
//            stopPlayerIntent.putExtra(BackgroundAudioPlayerPlugin.EXTRA_ACTION, "action.stop");
//            BackgroundAudioPlayerPlugin.mainActivity.startService(stopPlayerIntent);
//        }
        // TODO: implement this as it is important to stop playing when unplugged the ear phone
    }
}
