package com.frikiplanet.nasaapod.apod.data.datasources;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.data.local.AppDatabase;
import com.frikiplanet.nasaapod.core.domain.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.frikiplanet.nasaapod.apod.ApodDataProvider._2020_01_01;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider._2020_03_01;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider._LOWER_OUT_OF_BOUNDS;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider._NOT_STORED;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider._UPPER_OUT_OF_BOUNDS;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider.apods;
import static com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource.ERROR_DELETING_APOD;
import static com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource.ERROR_LOADING_APOD;
import static com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource.ERROR_SAVING_APOD;
import static org.junit.Assert.*;

public class LocalApodDataSourceTest {

    private AppDatabase database;
    private ApodDataSource apodDataSource;

    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                AppDatabase.class).build();

        apodDataSource = new LocalApodDataSource(database);
    }

    @After
    public void teardown() {
        database.close();
    }

    @Test
    public void loadApodShouldReturnErrorWhenDatabaseIsEmpty() {
        apodDataSource.loadApod(new Date(), response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_LOADING_APOD, ((Response.Error<Apod>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnSuccessWhenApodIsStoredInDatabase() {
        database.apodDao().saveApods(apods);

        apodDataSource.loadApod(_2020_01_01, response -> {
            if (response instanceof Response.Success) {
                assertNotNull(((Response.Success<Apod>) response).data);
            } else {
                fail("Success should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnSuccessWhenFromParameterIsNull() {
        database.apodDao().saveApods(apods);
        assertNotNull(database.apodDao().loadApod(null));
    }

    @Test
    public void loadApodShouldReturnErrorWhenFromDateIsNotStoredInDatabase() {
        database.apodDao().saveApods(apods);

        apodDataSource.loadApod(_NOT_STORED, response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_LOADING_APOD, ((Response.Error<Apod>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void saveApodShouldReturnSuccessWhenApodIsSuccessfullyStoredInDatabase() {
        Apod expectedApod = new Apod(new Date());
        apodDataSource.saveApod(expectedApod, response -> {
            if (response instanceof Response.Success) {
                assertEquals(expectedApod, ((Response.Success<Apod>) response).data);
            } else {
                fail("Success should be called");
            }
        });
    }

    @Test
    public void saveApodShouldReturnErrorWhenApodIsNull() {
        apodDataSource.saveApod(null, response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_SAVING_APOD, ((Response.Error<Apod>) response).error);

            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodsShouldReturnErrorWhenDatabaseIsEmpty() {
        apodDataSource.loadApods(response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_LOADING_APOD, ((Response.Error<List<Apod>>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodsShouldReturnSuccessWhenDatabaseIsPopulated() {
        database.apodDao().saveApods(apods);

        apodDataSource.loadApods(response -> {
            if (response instanceof Response.Success) {
                assertEquals(4, ((Response.Success<List<Apod>>) response).data.size());
            } else {
                fail("Success should be called");
            }
        });
    }

    @Test
    public void loadApodsBetweenDatesShouldReturnSuccessWhenThereAreApodsStoredInDatabaseBetweenFromAndTo() {
        database.apodDao().saveApods(apods);

        apodDataSource.loadApodsBetweenDates(_2020_01_01, _2020_03_01, response -> {
            if (response instanceof Response.Success) {
                assertEquals(3, ((Response.Success<List<Apod>>) response).data.size());
            } else {
                fail("Success should be called");
            }
        });
    }

    @Test
    public void loadApodsBetwwenDatesShouldReturnErrorWhenThereAreNotApodsStoredInDatabaseBetweenFromAndTo() {
        database.apodDao().saveApods(apods);

        apodDataSource.loadApodsBetweenDates(_LOWER_OUT_OF_BOUNDS, _UPPER_OUT_OF_BOUNDS, response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_LOADING_APOD, ((Response.Error<List<Apod>>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodsBetwwenDatesShouldReturnErrorWhenDatesAreToggle() {
        database.apodDao().saveApods(apods);

        apodDataSource.loadApodsBetweenDates(_UPPER_OUT_OF_BOUNDS, _LOWER_OUT_OF_BOUNDS, response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_LOADING_APOD, ((Response.Error<List<Apod>>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodsBetwwenDatesShouldReturnErrorWhenDatesAreWithinBoundsButAreToggle() {
        database.apodDao().saveApods(apods);

        apodDataSource.loadApodsBetweenDates(_2020_03_01, _2020_01_01, response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_LOADING_APOD, ((Response.Error<List<Apod>>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void deleteApodShouldReturnSuccessWhenApodIsSuccessfullyDeleteFromDatabase() {
        database.apodDao().saveApods(apods);

        apodDataSource.deleteApod(apods.get(0), response -> {
            if (response instanceof Response.Success) {
                assertEquals(apods.get(0), ((Response.Success<Apod>) response).data);

            } else {
                fail("Success should be called");
            }
        });

        assertEquals(3, database.apodDao().loadApods().size());
    }

    @Test
    public void deleteApodShouldReturnErrorWhenApodIsNotStoredInDatabase() {
        database.apodDao().saveApods(apods);

        apodDataSource.deleteApod(new Apod(_NOT_STORED), response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_DELETING_APOD, ((Response.Error<Apod>) response).error);

            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void deleteApodShouldReturnErrorWhenApodIsNull() {
        database.apodDao().saveApods(apods);

        apodDataSource.deleteApod(null, response -> {
            if (response instanceof Response.Error) {
                assertEquals(ERROR_DELETING_APOD, ((Response.Error<Apod>) response).error);

            } else {
                fail("Error should be called");
            }
        });
    }
}