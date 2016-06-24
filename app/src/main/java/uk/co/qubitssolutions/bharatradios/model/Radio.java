package uk.co.qubitssolutions.bharatradios.model;

public class Radio {
    private int id;
    private String name;
    private String description;
    private String subtext;
    private String streamUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
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
}
