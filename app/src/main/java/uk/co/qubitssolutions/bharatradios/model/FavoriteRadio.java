package uk.co.qubitssolutions.bharatradios.model;


public class FavoriteRadio {
    private int languageId;
    private int radioId;
    private boolean isFavorite;

    public int getRadioId() {
        return radioId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public boolean getIsFavorite(){
        return isFavorite;
    }

    public void setRadioId(int radioId) {
        this.radioId = radioId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public void setIsFavorite(boolean isFavorite){
        this.isFavorite = isFavorite;
    }
}
