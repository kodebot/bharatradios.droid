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

    private Radio radio;
    private String name;
    private String genre;
    private String imageUrl;
    private BharatRadiosApplication application;

    public RadioViewModel(BharatRadiosApplication application, Radio radio) {
        this.radio = radio;
        name = radio.getName();
        genre = radio.getGenre();
        imageUrl = radio.getImageUrl();
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getImageUrl() {
        return imageUrl;
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

