package uk.co.qubitssolutions.bharatradios.model;

public class Radio {
    private int id;
    private String name;
    private String description;
    private String subtext;
    private String streamUrl;
    private int languageId;
    private boolean isFavorite;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubtext() {
        return subtext;
    }

    public String getStreamUrl() {
        return streamUrl;
    }


    public String getDescription() {
        return description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
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

}
