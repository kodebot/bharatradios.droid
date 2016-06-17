package uk.co.qubitssolutions.bharatradios.app.viewholders;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.services.BackgroundAudioPlayerService;
import uk.co.qubitssolutions.bharatradios.model.Constants;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.AudioPlayer;


public class RadioListViewHolder extends RecyclerView.ViewHolder implements  AudioPlayer.StateChangeListener{
    TextView title;
    TextView subtitle;
    ImageView favImage;
    ImageView secondaryActionImage;
    String radioUrl;
    public RadioListViewHolder(View itemView) {
        super(itemView);

        title = (TextView)itemView.findViewById(R.id.list_item_radio_title);
        subtitle = (TextView)itemView.findViewById(R.id.list_item_radio_subtitle);
        favImage = (ImageView)itemView.findViewById(R.id.list_item_radio_fav);
        secondaryActionImage = (ImageView)itemView.findViewById(R.id.list_item_radio_secondary_action);
        CardView radioItem = (CardView) itemView.findViewById(R.id.radio_item);
        radioItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), title.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), BackgroundAudioPlayerService.class);
                intent.putExtra(Constants.EXTRA_ACTION, Constants.ACTION_PLAY);
                intent.putExtra(Constants.EXTRA_AUDIO_URL, radioUrl);
                intent.putExtra(Constants.EXTRA_RADIO_ID, 0);
                intent.putExtra(Constants.EXTRA_RADIO_NAME, title.getText());
                v.getContext().startService(intent);
            }
        });
    }

    public void setData(Radio radio){
        title.setText(radio.getName());
        subtitle.setText(radio.getSubtext());
        radioUrl = radio.getStreamUrl();
    }


    @Override
    public void onPlaying() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Exception ex) {

    }
}
