package uk.co.qubitssolutions.bharatradios.app;


import android.app.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.model.Radio;

public class BharatRadiosApplication extends Application {
    private RadioData radioData;

    public BharatRadiosApplication() {
        super();
        radioData = new RadioData();
    }

    public RadioData getRadioData() {
        return radioData;
    }

    public class RadioData {
        private boolean isCurrentlyPlaying;
        private float currentVolume;
        private int currentRadioIndex;
        private Date closeTime;
        private List<uk.co.qubitssolutions.bharatradios.model.Radio> radios;
        private boolean isInMute;

        public RadioData() {
            isCurrentlyPlaying = false;
            currentVolume = 0.5f;
            currentRadioIndex = 0;
            closeTime = null;
            radios = new ArrayList<>();
        }

        public boolean getIsCurrentlyPlaying() {
            return isCurrentlyPlaying;
        }

        public float getCurrentVolume() {
            return currentVolume;
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

        public Radio getCurrentRadio() {
            return radios.get(this.currentRadioIndex);
        }

        public boolean getIsInMute() {
            return isInMute;
        }

        public void setIsCurrentlyPlaying(boolean currentlyPlaying) {
            isCurrentlyPlaying = currentlyPlaying;
        }

        public void setCurrentVolume(float currentVolume) {
            this.currentVolume = currentVolume;
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

        public void setInMute(boolean inMute) {
            isInMute = inMute;
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
}
