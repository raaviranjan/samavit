package com.example.ravi.mfi;

/**
 * Created by Ravi on 04-Jul-18.
 */

public class SubModule2Blocks {

    private String title,url,moduleName,duration,source;
    private int imageUrl;

    public SubModule2Blocks(String title, int imageUrl, String url, String moduleName, String duration, String source){
        this.title = title;
        this.imageUrl = imageUrl;
        this.url = url;
        this.moduleName = moduleName;
        this.duration = duration;
        this.source = source;
    }

    public String getTitle(){
        return title;
    }
    public int getImageUrl(){
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getDuration() {
        return duration;
    }

    public String getSource() {
        return source;
    }
}
