package com.kamzee.app.models;

public class SliderItem {
    String title;
    String imageLink;
    String webLink;


    public SliderItem(String title, String imageLink, String webLink) {
        this.title = title;
        this.imageLink = imageLink;
        this.webLink = webLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }
}
