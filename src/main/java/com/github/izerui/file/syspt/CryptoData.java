package com.github.izerui.file.syspt;

public class CryptoData implements java.io.Serializable{

    private static final long serialVersionUID = -4774469372648172844L;

    private String key;

    private String content;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return "CryptoData [key=" + key + ", content=" + content + "]";
    }

}