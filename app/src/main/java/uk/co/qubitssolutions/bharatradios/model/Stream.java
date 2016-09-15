package uk.co.qubitssolutions.bharatradios.model;

public class Stream {
    private String src;
    private String srcName;
    private int bitRate;
    private String url;

    public Stream(String src, String srcName, int bitRate, String url) {
        this.src = src;
        this.srcName = srcName;
        this.bitRate = bitRate;
        this.url = url;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String value){
        src = value;
    }

    public String getSrcName() {
        return srcName;
    }

    public int getBitRate() {
        return bitRate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }


}
