package uk.co.qubitssolutions.bharatradios.model;


public class FavoriteLanguage {
    private int languageId;
    public FavoriteLanguage(int languageId, boolean favorite){
        this.languageId = languageId;
    }

    public int getLanguageId() {
        return languageId;
    }
}
