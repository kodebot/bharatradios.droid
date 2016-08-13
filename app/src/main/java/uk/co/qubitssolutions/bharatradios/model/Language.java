package uk.co.qubitssolutions.bharatradios.model;

public class Language {
    private int id;
    private String name;
    private String radiosUrl;

    public Language(int id, String name, String radiosUrl){
        this.id = id;
        this.name = name;
        this.radiosUrl = radiosUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRadiosUrl() {
        return radiosUrl;
    }
}
