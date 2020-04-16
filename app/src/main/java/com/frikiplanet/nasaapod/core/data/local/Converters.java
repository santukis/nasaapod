package com.frikiplanet.nasaapod.core.data.local;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class Converters {

    @TypeConverter
    public long fromDateToLong(Date date) {
        Date normalizedDate = date == null ? new Date() : date;
        return atStartOfDay(normalizedDate).getTime();
    }

    @TypeConverter
    public Date fromLongToDate(long timestamp) {
        return atStartOfDay(new Date(timestamp));
    }

    private Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
