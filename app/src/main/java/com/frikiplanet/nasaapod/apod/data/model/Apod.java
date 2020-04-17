package com.frikiplanet.nasaapod.apod.data.model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "apods")
public class Apod {

    public final static Apod EMPTY = new Apod();

    @PrimaryKey
    public Date date = new Date();

    public String title = "";

    @Json(name = "explanation") public String description = "";

    @ColumnInfo(name = "media_type")
    @Json(name = "media_type") public String mediaType = "";

    public String url = "";

    public String copyright = "";

    public Apod() { }

    public Apod(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Apod) &&
                Objects.equals(date, ((Apod) obj).date) &&
                Objects.equals(title, ((Apod) obj).title) &&
                Objects.equals(description, ((Apod) obj).description) &&
                Objects.equals(mediaType, ((Apod) obj).mediaType) &&
                Objects.equals(url, ((Apod) obj).url) &&
                Objects.equals(copyright, ((Apod) obj).copyright);
    }

    @Override
    public int hashCode() {
        int result = 13;
        if (date != null) result = 15 * result + date.hashCode();
        if (title != null)  result = 15 * result + title.hashCode();
        if (description != null)  result = 15 * result + description.hashCode();
        if (mediaType != null)  result = 15 * result + mediaType.hashCode();
        if (url != null)  result = 15 * result + url.hashCode();
        if (copyright != null) result = 15 * result + copyright.hashCode();
        return result;
    }
}
