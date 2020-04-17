package com.frikiplanet.nasaapod.core.data.local;

import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static com.frikiplanet.nasaapod.core.data.local.AppDatabase.MIGRATION_1_2;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    private static final String DATABASE_NAME = "test_migration";

    @Rule
    public MigrationTestHelper helper;

    @Before
    public void setup() {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                AppDatabase.class.getCanonicalName(),
                new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrate1To2() {
        try {
            SupportSQLiteDatabase database = helper.createDatabase(DATABASE_NAME, 1);

            database.execSQL("INSERT INTO apods (date, title, description, media_type) VALUES(1, 'title', 'description', 'media')");
            database.execSQL("INSERT INTO apods (date, title, description, media_type) VALUES(2, 'title', 'description', 'media')");
            database.execSQL("INSERT INTO apods (date, title, description, media_type) VALUES(3, 'title', 'description', 'media')");
            database.execSQL("INSERT INTO apods (date, title, description, media_type) VALUES(4, 'title', 'description', 'media')");
            database.execSQL("INSERT INTO apods (date, title, description, media_type) VALUES(5, 'title', 'description', 'media')");

            database.close();

            helper.runMigrationsAndValidate(DATABASE_NAME, 2, true, MIGRATION_1_2);

        } catch (IOException e) {
            e.printStackTrace();

            fail(e.getMessage());
        }
    }

}