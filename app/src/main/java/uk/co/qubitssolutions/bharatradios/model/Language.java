package uk.co.qubitssolutions.bharatradios.model;

public class Language {
    private int id;
    private String name;
    private String radiosUrl;
    private boolean favorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRadiosUrl() {
        return radiosUrl;
    }

    public void setRadiosUrl(String radiosUrl) {
        this.radiosUrl = radiosUrl;
    }

    public boolean getFavorite(){
        return favorite;
    }

    public void setFavorite(boolean value){
        favorite = value;
    }
}
