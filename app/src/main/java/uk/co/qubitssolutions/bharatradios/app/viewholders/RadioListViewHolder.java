package uk.co.qubitssolutions.bharatradios.app.viewholders;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.model.Constants;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.player.AudioPlayer;


public class RadioListViewHolder extends RecyclerView.ViewHolder
        implements AudioPlayer.StateChangeListener, View.OnClickListener {

    BharatRadiosApplication application;
    TextView title;
    TextView subtitle;
    ImageView favImage;
    Radio radio;
    ActionListener actionListener;

    public RadioListViewHolder(View itemView, Application application, ActionListener actionListener) {
        super(itemView);
        this.application = (BharatRadiosApplication) application;
        title = (TextView) itemView.findViewById(R.id.list_item_radio_title);
        subtitle = (TextView) itemView.findViewById(R.id.list_item_radio_subtitle);
        favImage = (ImageView) itemView.findViewById(R.id.action_list_item_radio_fav);

        this.actionListener = actionListener;

        LinearLayout radioItem = (LinearLayout) itemView.findViewById(R.id.radio_item);
        radioItem.setOnClickListener(this);

    }

    public void setData(Radio radio) {
        this.radio = radio;
        title.setText(radio.getName());
        subtitle.setText(radio.getSubtext());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_item:
                application.getRadioData().setCurrentRadioIndex(application.getRadioData().getRadios().indexOf(radio));
                actionListener.run(Constants.ACTION_PLAY);
                break;
        }
    }

    public interface ActionListener {
        void run(String action);
    }


    /**********************************************************************************************
     * ********************************* PRIVATE METHODS ******************************************
     ********************************************************************************************/

}
