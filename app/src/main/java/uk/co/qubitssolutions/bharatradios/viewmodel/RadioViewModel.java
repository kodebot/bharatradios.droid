package uk.co.qubitssolutions.bharatradios.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.services.BackgroundAudioPlayerService;
import uk.co.qubitssolutions.bharatradios.model.Constants;
import uk.co.qubitssolutions.bharatradios.model.FavoriteRadio;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteRadioPreferenceService;

@BindingMethods({
        @BindingMethod(type = AppCompatImageButton.class,
                attribute = "app:srcCompat",
                method = "setImageResource") })
public class RadioViewModel extends BaseObservable {

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

    private Radio radio;
    private String name;
    private String genre;
    private BharatRadiosApplication application;

    public RadioViewModel(BharatRadiosApplication application, Radio radio) {
        this.radio = radio;
        name = radio.getName();
        genre = radio.getGenre();
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    @Bindable
    public boolean isFavorite() {
        List<FavoriteRadio> favRadios = FavoriteRadioPreferenceService
                .getInstance(application)
                .getAll();
        for (FavoriteRadio favRadio : favRadios) {
            if (favRadio.getRadioId() == this.radio.getId() &&
                    favRadio.getLanguageId() == this.radio.getLanguageId()) {
                return true;
            }
        }
        return false;
    }

    public String getInitial() {
        String result = "";
        String[] textParts = name.split(" ");
        for (String textPart : textParts) {
            result = result + textPart.charAt(0);
            if (result.length() == 2) {
                break;
            }
        }
        return result;
    }

    public Drawable getAvatarImage() {
        return ResourcesCompat.getDrawable(
                application.getResources(),
                avatarImages.get(getName().length() % avatarImages.size()),
                null);
    }

    public void toggleFavorite(View view) {
        FavoriteRadio favRadio = new FavoriteRadio(this.radio.getLanguageId(), this.radio.getId());
        FavoriteRadioPreferenceService.getInstance(application).update(favRadio);
        notifyPropertyChanged(BR.favorite);
    }

    public void onClick(View view){
        this.application.setCurrentRadio(radio);
        Intent intent = new Intent(this.application, BackgroundAudioPlayerService.class);
        intent.putExtra(Constants.EXTRA_ACTION, Constants.ACTION_PLAY);
        this.application.startService(intent);
    }


}

