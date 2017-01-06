package uk.co.qubitssolutions.bharatradios.model;

import java.util.ArrayList;

public class Radio {
    private int id;
    private String name;
    private String desc;
    private String genre;
    private ArrayList<Stream> streams;
    private int languageId;
    private String imageUrl;

    public Radio(int id, String name, String desc, String genre, String imageUrl, int languageId) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.genre = genre;
        this.languageId = languageId;
        this.imageUrl = imageUrl;
        streams = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }

    public void addStream(Stream stream) {
        this.streams.add(stream);
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLanguageId() {
        return languageId;
    }
}
