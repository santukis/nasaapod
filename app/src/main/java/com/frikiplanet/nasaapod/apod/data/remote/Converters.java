package com.frikiplanet.nasaapod.apod.data.remote;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {

    public @FromJson Date fromJsonToDate(String json) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(json);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public @ToJson String fromDateToString(Date date) {
        Date desiredDate = date == null ? new Date() : date;
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(desiredDate);
    }
}
