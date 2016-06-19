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
}
