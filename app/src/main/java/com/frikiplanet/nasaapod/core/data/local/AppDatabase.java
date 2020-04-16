package com.frikiplanet.nasaapod.core.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.frikiplanet.nasaapod.apod.data.local.ApodDao;
import com.frikiplanet.nasaapod.apod.data.model.Apod;

@Database(entities = {Apod.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public abstract ApodDao apodDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "nasa.db").build();
        }

        return instance;
    }
}
