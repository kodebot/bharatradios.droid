package uk.co.qubitssolutions.bharatradios.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.app.services.BackgroundAudioPlayerService;
import uk.co.qubitssolutions.bharatradios.model.Constants;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.model.Stream;

public class PlayerViewModel extends BaseObservable {
    private boolean playing;
    private boolean busy;
    private String radioName;
    private String radioGenre;
    private String currentlyPlaying;
    private String bitRate;

    public PlayerViewModel() {
        playing = false;
        radioName = "Please select a radio to play";
        radioGenre = "";
        currentlyPlaying = "";
        bitRate = "";
    }

    @Bindable
    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
        notifyPropertyChanged(BR.playing);
    }

    @Bindable
    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
        notifyPropertyChanged(BR.busy);
    }

    @Bindable
    public String getRadioName() {
        return radioName;
    }

    private void setRadioName(String radioName) {
        this.radioName = radioName;
        notifyPropertyChanged(BR.radioName);
    }

    @Bindable
    public String getRadioGenre() {
        return radioGenre;
    }

    private void setRadioGenre(String radioGenre) {
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

    private void setBitRate(String bitRate) {
        this.bitRate = bitRate;
        notifyPropertyChanged(BR.bitRate);
    }

    public void setCurrentRadio(Radio currentRadio) {
        setRadioName(currentRadio.getName());
        setRadioGenre(currentRadio.getGenre());
    }

    public void setCurrentStream(Stream currentStream) {
        setBitRate(String.valueOf(currentStream.getBitRate()));
    }

    public void togglePlay(View view) {
        Intent intent = new Intent(view.getContext(), BackgroundAudioPlayerService.class);
        intent.putExtra(Constants.EXTRA_ACTION, playing ? Constants.ACTION_STOP : Constants.ACTION_PLAY);
        view.getContext().startService(intent);

    }

}

