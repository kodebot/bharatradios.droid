package uk.co.qubitssolutions.bharatradios.model;

public class Radio {
    private int id;
    private String name;
    private String desc;
    private String genre;
    private Stream[] streams;
    private int languageId;
    private boolean isFavorite;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public Stream[] getStreams() {
        return streams;
    }


    public String getDesc() {
        return desc;
    }

    public int getLanguageId(){
        return languageId;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setStreams(Stream[] streams) {
        this.streams = streams;
    }
}
