package uk.co.qubitssolutions.bharatradios.model;


public class FavoriteLanguage {
    private int languageId;
    private boolean isFavorite;

    public int getLanguageId() {
        return languageId;
    }

    public boolean getIsFavorite(){
        return isFavorite;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public void setIsFavorite(boolean isFavorite){
        this.isFavorite = isFavorite;
    }
}
