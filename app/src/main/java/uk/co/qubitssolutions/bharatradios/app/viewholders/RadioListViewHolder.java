package uk.co.qubitssolutions.bharatradios.app.viewholders;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.model.Constants;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.player.AudioPlayer;


public class RadioListViewHolder extends RecyclerView.ViewHolder
        implements AudioPlayer.StateChangeListener, View.OnClickListener {

    private final static List<Integer> avatarImages;

    static {
        avatarImages = new ArrayList<>();
        avatarImages.add(R.drawable.radio_item_avatar_blue);
        avatarImages.add(R.drawable.radio_item_avatar_blue_grey);
        avatarImages.add(R.drawable.radio_item_avatar_brown);
        avatarImages.add(R.drawable.radio_item_avatar_dark_purple);
        avatarImages.add(R.drawable.radio_item_avatar_deep_orange);
        avatarImages.add(R.drawable.radio_item_avatar_green);
        avatarImages.add(R.drawable.radio_item_avatar_indigo);
        avatarImages.add(R.drawable.radio_item_avatar_orange);
        avatarImages.add(R.drawable.radio_item_avatar_pink);
        avatarImages.add(R.drawable.radio_item_avatar_purple);
        avatarImages.add(R.drawable.radio_item_avatar_red);
        avatarImages.add(R.drawable.radio_item_avatar_teal);
    }

    private TextView avatarText;
    private ImageView avatarImage;
    private BharatRadiosApplication application;
    private TextView title;
    private TextView subtitle;
    private ImageView favImage;
    private Radio radio;
    private ActionListener actionListener;

    public RadioListViewHolder(View itemView, Application application, ActionListener actionListener) {
        super(itemView);
        this.application = (BharatRadiosApplication) application;
        title = (TextView) itemView.findViewById(R.id.list_item_radio_title);
        subtitle = (TextView) itemView.findViewById(R.id.list_item_radio_subtitle);
        favImage = (ImageView) itemView.findViewById(R.id.action_list_item_radio_fav);
        avatarImage = (ImageView) itemView.findViewById(R.id.list_item_radio_avatar);
        avatarText = (TextView) itemView.findViewById(R.id.list_item_radio_avatar_text);

        this.actionListener = actionListener;

        LinearLayout radioItem = (LinearLayout) itemView.findViewById(R.id.radio_item);
        radioItem.setOnClickListener(this);

    }

    public void setData(Radio radio) {
        this.radio = radio;
        title.setText(radio.getName());
        subtitle.setText(radio.getSubtext());
        avatarText.setText(getInitials(radio.getName()));

        avatarImage.setImageResource(avatarImages.get(radio.getName().length() % avatarImages.size()));

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

    private String getInitials(String text) {
        String result = "";
        String[] textParts = text.split(" ");
        for (String textPart : textParts) {
            result = result + textPart.charAt(0);
            if (result.length() == 2) {
                break;
            }
        }
        return result;
    }

}
