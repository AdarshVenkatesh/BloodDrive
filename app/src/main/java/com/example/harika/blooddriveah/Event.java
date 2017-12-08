package com.example.harika.blooddriveah;

/**
 * Created by adars on 12/4/2017.
 */

public class Event {
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    String description;
    String picture;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    String caption;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
}
