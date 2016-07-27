package uk.co.qubitssolutions.bharatradios.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.model.FavoriteRadio;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.model.Stream;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteRadioPreferenceService;

public class PlayerViewModel extends BaseObservable {
    private boolean isPlaying;
    private Date closeTime;
    private String radioName;
    private String radioGenre;
    private String currentlyPlaying;
    private String bitRate;
    private Context context;
    private List<Radio> radios;
    private Radio currentRadio;
    private Stream currentStream;

    public PlayerViewModel(Context context) {
        isPlaying = false;
        closeTime = null;
        radioName = "Please select a radio to play";
        radioGenre = "";
        currentlyPlaying = "";
        bitRate = "";
        this.context = context;
        radios = new ArrayList<>();
    }

    @Bindable
    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean playing) {
        isPlaying= playing;
        notifyPropertyChanged(BR.isPlaying);
    }

    @Bindable
    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
        notifyPropertyChanged(BR.closeTime);
    }

    @Bindable
    public List<Radio> getRadios() {
        return radios;
    }

    public void setRadios(List<Radio> radios) {
        this.radios = radios;
        notifyPropertyChanged(BR.radios);
    }

    public List<Radio> getFavoriteRadios(){
        List<Radio> favRadios = new ArrayList<>();
        List<FavoriteRadio> favorites = FavoriteRadioPreferenceService.getInstance(context)
                .getAll();

        for(Radio radio:radios){
            for(FavoriteRadio fav:favorites){
                if(radio.getId() == fav.getRadioId() &&
                        radio.getLanguageId() == fav.getLanguageId()){
                    radio.setIsFavorite(true);
                    favRadios.add(radio);
                }
            }
        }

        return favRadios;
    }

    @Bindable
    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
        notifyPropertyChanged(BR.radioName);
    }

    @Bindable
    public String getRadioGenre() {
        return radioGenre;
    }

    public void setRadioGenre(String radioGenre) {
        this.radioGenre = radioGenre;
        notifyPropertyChanged(BR.radioGenre);
    }

    @Bindable
    public String getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(String currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
        notifyPropertyChanged(BR.currentlyPlaying);
    }

    @Bindable
    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
        notifyPropertyChanged(BR.bitRate);
    }

    public Radio getCurrentRadio() {
        return currentRadio;
    }

    public void setCurrentRadio(Radio currentRadio) {
        this.currentRadio = currentRadio;
        setRadioName(currentRadio.getName());
        setRadioGenre(currentRadio.getGenre());
    }

    public Stream getCurrentStream() {
        return currentStream;
    }

    public void setCurrentStream(Stream currentStream) {
        this.currentStream = currentStream;
        setBitRate(String.valueOf(currentStream.getBitRate()));
    }
}

