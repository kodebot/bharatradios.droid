package uk.co.qubitssolutions.bharatradios.app.viewholders;

import android.app.Application;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.adapters.RadioListRecyclerAdapter;
import uk.co.qubitssolutions.bharatradios.model.Constants;
import uk.co.qubitssolutions.bharatradios.model.FavoriteRadio;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.player.AudioPlayer;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteRadioPreferenceService;


public class RadioListItemViewHolder extends RecyclerView.ViewHolder
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

    private CardView listItemCard;
    private TextView avatarText;
    private ImageView avatarImage;
    private BharatRadiosApplication application;
    private TextView title;
    private TextView subtitle;
    private AppCompatImageButton favImage;
    private Radio radio;
    private RadioListRecyclerAdapter.SelectionChangeListener selectionChangeListener;
    private ActionListener actionListener;

    public RadioListItemViewHolder(View itemView, Application application, ActionListener actionListener) {
        super(itemView);
        this.application = (BharatRadiosApplication) application;
        listItemCard = (CardView) itemView;
        title = (TextView) itemView.findViewById(R.id.list_item_radio_title);
        subtitle = (TextView) itemView.findViewById(R.id.list_item_radio_subtitle);
        favImage = (AppCompatImageButton) itemView.findViewById(R.id.action_list_item_radio_fav);
        avatarImage = (ImageView) itemView.findViewById(R.id.list_item_radio_avatar);
        avatarText = (TextView) itemView.findViewById(R.id.list_item_radio_avatar_text);

        this.actionListener = actionListener;

        LinearLayout radioItem = (LinearLayout) itemView.findViewById(R.id.radio_item);
        radioItem.setOnClickListener(this);
        favImage.setOnClickListener(this);
    }

    public void setData(Radio radio,
                        RadioListRecyclerAdapter.SelectionChangeListener selectionChangeListener,
                        boolean isSelected) {
        this.radio = radio;
        this.selectionChangeListener = selectionChangeListener;
        title.setText(radio.getName());
        subtitle.setText(radio.getSubtext());
        avatarText.setText(getInitials(radio.getName()));
        setupFavRadio();
        avatarImage.setImageResource(avatarImages.get(radio.getName().length() % avatarImages.size()));
        if(isSelected){
            listItemCard.requestFocus();
        }
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
                listItemCard.requestFocus();
                application.getRadioData().setCurrentRadioIndex(application.getRadioData().getRadios().indexOf(radio));
                actionListener.run(Constants.ACTION_PLAY);
                this.selectionChangeListener.onChange(getAdapterPosition());
                break;
            case R.id.action_list_item_radio_fav:
                toggleFavorite(radio, view);
                break;
        }
    }

    public void updataSelection(int selectedItem) {
        if(getLayoutPosition() == selectedItem){
            listItemCard.requestFocus();
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

    private void setupFavRadio() {
        List<FavoriteRadio> favoriteRadios = FavoriteRadioPreferenceService
                .getInstance(this.application.getApplicationContext())
                .getAll();
        for (FavoriteRadio favRadio : favoriteRadios) {
            if (favRadio.getLanguageId() == radio.getLanguageId() &&
                    favRadio.getRadioId() == radio.getId()) {
                this.radio.setIsFavorite(true);
                break;
            }
        }

        updateFavIcon(favImage);
    }

    private void toggleFavorite(Radio radio, View view) {
        radio.setIsFavorite(!radio.getIsFavorite());
        FavoriteRadio favRadio = new FavoriteRadio();
        favRadio.setRadioId(radio.getId());
        favRadio.setLanguageId(radio.getLanguageId());
        FavoriteRadioPreferenceService.getInstance(view.getContext()).update(favRadio);
        updateFavIcon((AppCompatImageButton) view);

    }

    private void updateFavIcon(AppCompatImageButton view) {
        if (radio.getIsFavorite()) {
            view.setBackgroundResource(R.drawable.ic_favorite_white_24dp_wrapped);
            view.setSupportBackgroundTintList(new ColorStateList(
                    new int[][]{new int[0]},
                    new int[]{ContextCompat.getColor(view.getContext(), R.color.colorAccent)}));
        } else {
            view.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp_wrapped);
            view.setSupportBackgroundTintList(new ColorStateList(
                    new int[][]{new int[0]},
                    new int[]{ContextCompat.getColor(view.getContext(), R.color.colorAccent)}));
        }
    }


}
