package com.frikiplanet.nasaapod.core.data.remote;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    @FromJson public static Date fromJsonToDate(String json) {
        try {
            return createFormatter(YYYY_MM_DD).parse(json);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @ToJson public static String fromDateToString(Date date) {
        Date desiredDate = date == null ? new Date() : date;
        return createFormatter(YYYY_MM_DD).format(desiredDate);
    }

    private static SimpleDateFormat createFormatter(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault());
    }
}
