package uk.co.qubitssolutions.bharatradios.app;


import android.app.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import uk.co.qubitssolutions.bharatradios.model.FavoriteRadio;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteRadioPreferenceService;

public class BharatRadiosApplication extends Application {
    private Map<Language,RadioData> radioDataMap;
    private ToolBarData toolBarData;
    private LanguageData languageData;

    public BharatRadiosApplication() {
        super();
        radioDataMap = new Hashtable<>();
        toolBarData = new ToolBarData();
        languageData = new LanguageData();
    }

    public RadioData getRadioData(){
        return getRadioData(getLanguageData().getCurrentLanguage());
    }

    public RadioData getRadioData(Language language) {
        RadioData radioData = radioDataMap.get(language);

        if(radioData == null){
            radioData = new RadioData();
            radioDataMap.put(language, radioData);
        }

        return radioData;
    }

    public ToolBarData getToolBarData(){
        return toolBarData;
    }

    public LanguageData getLanguageData() {
        return languageData;
    }

    public class ToolBarData {
        private boolean isFavoriteOnly;

        public boolean getIsFavoriteOnly() {
            return isFavoriteOnly;
        }

        public void setIsFavoriteOnly(boolean favoriteOnly) {
            isFavoriteOnly = favoriteOnly;
        }
    }

    public class RadioData {
        private boolean isCurrentlyPlaying;
        private int currentRadioIndex;
        private Date closeTime;
        private List<uk.co.qubitssolutions.bharatradios.model.Radio> radios;

        public RadioData() {
            isCurrentlyPlaying = false;
            currentRadioIndex = 0;
            closeTime = null;
            radios = new ArrayList<>();
        }

        public boolean getIsCurrentlyPlaying() {
            return isCurrentlyPlaying;
        }

        public int getCurrentRadioIndex() {
            return currentRadioIndex;
        }

        public Date getCloseTime() {
            return closeTime;
        }

        public List<Radio> getRadios() {
            return radios;
        }

        public List<Radio> getFavoriteRadios(){
            List<Radio> favRadios = new ArrayList<>();
            List<FavoriteRadio> favorites = FavoriteRadioPreferenceService.getInstance(BharatRadiosApplication.this)
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

        public Radio getCurrentRadio() {
            return radios.get(this.currentRadioIndex);
        }


        public void setIsCurrentlyPlaying(boolean currentlyPlaying) {
            isCurrentlyPlaying = currentlyPlaying;
        }

        public void setCurrentRadioIndex(int currentRadioIndex) {
            this.currentRadioIndex = currentRadioIndex;
        }

        public void setCloseTime(Date closeTime) {
            this.closeTime = closeTime;
        }

        public void setRadios(List<Radio> radios) {
            this.radios = radios;
        }

        public void moveToNextRadio() {
            if (++this.currentRadioIndex >= this.radios.size()) {
                this.currentRadioIndex = 0;
            }
        }

        public void moveToPreviousRadio() {
            if (--this.currentRadioIndex < 0) {
                this.currentRadioIndex = this.radios.size() - 1;
            }
        }
    }

    public class LanguageData{
        private List<Language> languages;
        private Language currentLanguage;

        public List<Language> getLanguages(){
            return languages;
        }

        public Language getCurrentLanguage() {
            return currentLanguage;
        }

        public void setLanguages(List<Language> languages){
            this.languages = languages;
        }

        public void setCurrentLanguage(Language currentLanguage) {
            this.currentLanguage = currentLanguage;
        }
    }
}
