package com.frikiplanet.nasaapod.apod.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.util.Date;

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
}
