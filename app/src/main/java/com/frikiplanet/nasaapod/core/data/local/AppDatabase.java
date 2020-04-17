package com.frikiplanet.nasaapod.core.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.frikiplanet.nasaapod.apod.data.local.ApodDao;
import com.frikiplanet.nasaapod.apod.data.model.Apod;

@Database(entities = {Apod.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public abstract ApodDao apodDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "nasa.db")
                    .addMigrations(MIGRATION_1_2).build();
        }

        return instance;
    }

    static final Migration MIGRATION_1_2  = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE apods ADD COLUMN copyright TEXT DEFAULT ''");
        }
    };
}
