package com.frikiplanet.nasaapod.apod.data.repository;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.datasources.LocalApodDataSource;
import com.frikiplanet.nasaapod.apod.data.datasources.RemoteApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.data.local.AppDatabase;
import com.frikiplanet.nasaapod.core.data.remote.Converters;
import com.frikiplanet.nasaapod.core.data.remote.HttpClient;
import com.frikiplanet.nasaapod.core.domain.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static com.frikiplanet.nasaapod.apod.ApodDataProvider.serverResponseErrorApiKeyMissing;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider.serverResponseErrorDateOutOfBounds;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider.serverResponseErrorWrongApiKey;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider.serverResponseErrorWrongFormatDate;
import static com.frikiplanet.nasaapod.apod.ApodDataProvider.serverResponseOk;
import static org.junit.Assert.*;

public class ApodRepositoryIntegrationTest {

    private AppDatabase database;
    private MockWebServer mockWebServer;

    private ApodDataSource apodRepository;


    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                AppDatabase.class).build();
        ApodDataSource localApodDataSource = new LocalApodDataSource(database);

        mockWebServer = new MockWebServer();
        HttpClient client = new HttpClient(mockWebServer.url("").toString());
        ApodDataSource remoteApodDataSource = new RemoteApodDataSource(client);

        apodRepository = new ApodRepository(localApodDataSource, remoteApodDataSource);
    }

    @After
    public void teardown() {
        try {
            database.close();
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadApodShouldReturnSuccessWhenApodIsStoredInDatabase() {
        database.apodDao().saveApod( new Apod(new Date()));

        apodRepository.loadApod(new Date(), response -> {
            if (response instanceof Response.Success) {
                assertNotNull(((Response.Success<Apod>) response).data);
            } else {
                fail("Success should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnSuccessWhenApodIsReturnFromWebService() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(serverResponseOk));

        Date expectedDate = new Date(120, 3, 14);

        apodRepository.loadApod(new Date(), response -> {
            if (response instanceof Response.Success) {
                Apod apod = ((Response.Success<Apod>) response).data;

                assertEquals(expectedDate, apod.date);
                assertEquals("NGC 253 is one of the brightest spiral galaxies visible, but also one of the dustiest.", apod.description);
                assertEquals("image", apod.mediaType);
                assertEquals("NGC 253: The Silver Coin Galaxy", apod.title);
                assertEquals("https://apod.nasa.gov/apod/image/2004/NGC253_HstSubaruEsoNew_960.jpg", apod.url);
            } else {
                fail("Success should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenFromDateIsOutOfBounds() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(serverResponseErrorDateOutOfBounds));

        apodRepository.loadApod(new Date(), response -> {
            if (response instanceof Response.Error) {
                assertEquals(400, ((Response.Error<Apod>) response).code);
                assertEquals("Date must be between Jun 16, 1995 and Apr 15, 2020.", ((Response.Error<Apod>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenApiKeyIsMissing() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(403).setBody(serverResponseErrorApiKeyMissing));

        apodRepository.loadApod(new Date(), response -> {
            if (response instanceof Response.Error) {
                assertEquals(403, ((Response.Error<Apod>) response).code);
                assertEquals("No api_key was supplied. Get one at https://api.nasa.gov:443", ((Response.Error<Apod>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenApiKeyIsWrong() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(403).setBody(serverResponseErrorWrongApiKey));

        apodRepository.loadApod(new Date(), response -> {
            if (response instanceof Response.Error) {
                assertEquals(403, ((Response.Error<Apod>) response).code);
                assertEquals("An invalid api_key was supplied. Get one at https://api.nasa.gov:443", ((Response.Error<Apod>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenDateFormatIsWrong() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(serverResponseErrorWrongFormatDate));

        apodRepository.loadApod(new Date(), response -> {
            if (response instanceof Response.Error) {
                assertEquals(400, ((Response.Error<Apod>) response).code);
                assertEquals("time data 'WrongFormat' does not match format '%Y-%m-%d'", ((Response.Error<Apod>) response).error);
            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodShouldBuildTheRequestSuccessfullyWhenDateIsNotNull() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

        Date expectedDate = new Date(120, 3, 14);

        apodRepository.loadApod(expectedDate, response -> {});

        try {
            RecordedRequest request = mockWebServer.takeRequest();
            assertEquals("/planetary/apod?date=2020-04-14&hd=true&api_key=DEMO_KEY", request.getPath());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadApodShouldBuildTheRequestSuccessfullyWhenDateIsNull() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

        String expectedDate = new Converters().fromDateToString(new Date());

        apodRepository.loadApod(null, response -> {});

        try {
            RecordedRequest request = mockWebServer.takeRequest();
            assertEquals("/planetary/apod?date=" + expectedDate + "&hd=true&api_key=DEMO_KEY", request.getPath());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}