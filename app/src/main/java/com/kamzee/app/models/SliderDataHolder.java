package com.kamzee.app.models;

public class SliderDataHolder {
    String imageLink;
    String title;

    public SliderDataHolder() {
    }

    public SliderDataHolder(String imageLink, String title) {
        this.imageLink = imageLink;
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
