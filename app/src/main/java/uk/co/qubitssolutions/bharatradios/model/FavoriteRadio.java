package uk.co.qubitssolutions.bharatradios.model;

public class FavoriteRadio {
    private int languageId;
    private int radioId;

    public FavoriteRadio(int languageId, int radioId){
        this.languageId = languageId;
        this.radioId = radioId;
    }

    public int getRadioId() {
        return radioId;
    }

    public int getLanguageId() {
        return languageId;
    }
}
