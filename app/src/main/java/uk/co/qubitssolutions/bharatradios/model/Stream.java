package uk.co.qubitssolutions.bharatradios.model;

public class Stream {
    private int bitRate;
    private String url;

    public Stream(int bitRate, String url) {

        this.bitRate = bitRate;
        this.url = url;
    }

    public int getBitRate() {
        return bitRate;
    }

    public String getUrl() {
        return url;
    }

}
