package uk.co.qubitssolutions.bharatradios.app;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import rx.subjects.BehaviorSubject;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.model.PlayerStatusType;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.model.Stream;
import uk.co.qubitssolutions.bharatradios.viewmodel.PlayerViewModel;
import uk.co.qubitssolutions.bharatradios.viewmodel.ToolbarViewModel;

public class BharatRadiosApplication extends Application {

    private static Context context;
    private int currentLanguageId;
    private Map<Integer, List<Radio>> languageRadioMap;
    private List<Language> languages;
    private ToolbarViewModel toolbarViewModel;
    private PlayerViewModel playerViewModel;
    private Radio currentRadio;
    private PlayerStatusType playerStatus;
    private Stream currentStream;
    private boolean recordAudioPermisssion;

    public BehaviorSubject<byte[]> audioVisualizerData = BehaviorSubject.create();


    public BharatRadiosApplication() {
        super();
        languageRadioMap = new Hashtable<>();
        languages = new ArrayList<>();
        toolbarViewModel = new ToolbarViewModel();
        playerViewModel = new PlayerViewModel();
        playerStatus = PlayerStatusType.STOPPED;
    }

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public List<Radio> getRadios(int languageId) {
        return languageRadioMap.get(languageId);
    }

    public void setRadios(int languageId, List<Radio> radios) {
        this.languageRadioMap.put(languageId, radios);
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public ToolbarViewModel getToolbarViewModel() {
        return toolbarViewModel;
    }

    public PlayerViewModel getPlayerViewModel() {
        return playerViewModel;
    }

    public int getCurrentLanguageId() {
        return currentLanguageId;
    }

    public void setCurrentLanguageId(int currentLanguageId) {
        this.currentLanguageId = currentLanguageId;
    }

    public PlayerStatusType getPlayerStatus() {
        return playerStatus;
    }

    public boolean isPlaying() {
        return PlayerStatusType.PLAYING == playerStatus;
    }

    public void setPlayerStatus(PlayerStatusType playerStatus) {
        this.playerStatus = playerStatus;
        switch (playerStatus) {
            case PLAYING:
                this.playerViewModel.setPlaying(true);
                this.playerViewModel.setBusy(false);
                break;
            case BUFFERING:
                this.playerViewModel.setBusy(true);
                break;
            case STOPPED:
                this.playerViewModel.setPlaying(false);
                this.playerViewModel.setBusy(false);
        }

    }

    public Radio getCurrentRadio() {
        return currentRadio;
    }

    public void setCurrentRadio(Radio radio) {
        currentRadio = radio;
        this.playerViewModel.setCurrentRadio(radio);
    }

    public Stream getCurrentStream() {
        return currentStream;
    }

    public void setCurrentStream(Stream currentStream) {
        this.currentStream = currentStream;
        this.playerViewModel.setCurrentStream(currentStream);
    }

    public void updateCurrentlyPlaying(String currentlyPlaying) {
        this.playerViewModel.setCurrentlyPlaying(currentlyPlaying);
    }

    public static Context getContext() {
        return context;
    }

    public boolean hasRecordAudioPermisssion() {
        return recordAudioPermisssion;
    }

    public void setRecordAudioPermisssion(boolean recordAudioPermisssion) {
        this.recordAudioPermisssion = recordAudioPermisssion;
    }
}
