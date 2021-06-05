package com.kamzee.app.models;

public class SliderItem {
    String imageLink;
    String webLink;

    public SliderItem(String imageLink, String webLink) {
        this.imageLink = imageLink;
        this.webLink = webLink;
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
