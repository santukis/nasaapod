package com.frikiplanet.nasaapod.core.data.local;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public long fromDateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date fromLongToDate(long timestamp) {
        return new Date(timestamp);
    }
}
