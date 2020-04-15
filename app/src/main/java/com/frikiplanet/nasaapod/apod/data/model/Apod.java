package com.frikiplanet.nasaapod.apod.data.model;

import com.squareup.moshi.Json;

import java.util.Date;

public class Apod {

    public final static Apod EMPTY = new Apod();

    private String title = "";
    @Json(name = "explanation") private String description = "";
    private Date date = new Date();
    @Json(name = "media_type") private String mediaType = "";
    private String url = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
