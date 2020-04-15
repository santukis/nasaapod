package com.frikiplanet.nasaapod.apod.data.datasources;

import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.data.remote.Converters;
import com.frikiplanet.nasaapod.apod.data.remote.HttpClient;
import com.frikiplanet.nasaapod.core.domain.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;

import static com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource.ERROR_LOADING_APOD;
import static org.junit.Assert.*;

public class RemoteApodDataSourceTest {


    private MockWebServer mockWebServer;
    private ApodDataSource apodDataSource;

    @Before
    public void setup() {
        mockWebServer = new MockWebServer();
        HttpClient client = new HttpClient(mockWebServer.url("").toString());
        apodDataSource = new RemoteApodDataSource(client);
    }

    @After
    public void teardown() {
        try {
            mockWebServer.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadApodShouldReturnSuccesWhenServerResponseIsOk() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(RemoteApodDataProvider.serverResponseOk));

        Date expectedDate = new Date(120, 3, 14);

        apodDataSource.loadApod(expectedDate, response -> {
            if (response instanceof Response.Success) {
                Apod apod = ((Response.Success<Apod>) response).data;

                assertEquals(expectedDate, apod.getDate());
                assertEquals("NGC 253 is one of the brightest spiral galaxies visible, but also one of the dustiest.", apod.getDescription());
                assertEquals("image", apod.getMediaType());
                assertEquals("NGC 253: The Silver Coin Galaxy", apod.getTitle());
                assertEquals("https://apod.nasa.gov/apod/image/2004/NGC253_HstSubaruEsoNew_960.jpg", apod.getUrl());

            } else if (response instanceof Response.Error) {
                fail("Success should be called but was Error");
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenApiKeyIsMissing() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(403).setBody(RemoteApodDataProvider.serverResponseErrorApiKeyMissing));

        apodDataSource.loadApod(new Date(), response -> {
            if (response instanceof Response.Success) {
                fail("Error should be called but was Success");
            }

            if (response instanceof Response.Error) {
                assertEquals(403, ((Response.Error<Apod>) response).code);
                assertEquals("No api_key was supplied. Get one at https://api.nasa.gov:443", ((Response.Error<Apod>) response).error);
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenApiKeyIsWrong() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(403).setBody(RemoteApodDataProvider.serverResponseErrorWrongApiKey));

        apodDataSource.loadApod(new Date(), response -> {
            if (response instanceof Response.Success) {
                fail("Error should be called but was Success");
            }

            if (response instanceof Response.Error) {
                assertEquals(403, ((Response.Error<Apod>) response).code);
                assertEquals("An invalid api_key was supplied. Get one at https://api.nasa.gov:443", ((Response.Error<Apod>) response).error);
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenDateIsOutOfBounds() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(RemoteApodDataProvider.serverResponseErrorDateOutOfBounds));

        apodDataSource.loadApod(new Date(), response -> {
            if (response instanceof Response.Success) {
                fail("Error should be called but was Success");
            }

            if (response instanceof Response.Error) {
                assertEquals(400, ((Response.Error<Apod>) response).code);
                assertEquals("Date must be between Jun 16, 1995 and Apr 15, 2020.", ((Response.Error<Apod>) response).error);
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenDateFormatIsWrong() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(RemoteApodDataProvider.serverResponseErrorWrongFormatDate));

        apodDataSource.loadApod(new Date(), response -> {
            if (response instanceof Response.Success) {
                fail("Error should be called but was Success");
            }

            if (response instanceof Response.Error) {
                assertEquals(400, ((Response.Error<Apod>) response).code);
                assertEquals("time data 'WrongFormat' does not match format '%Y-%m-%d'", ((Response.Error<Apod>) response).error);
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenSocketTimeOutExceptionIsThrown() {
        mockWebServer.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));

        apodDataSource.loadApod(new Date(), response -> {
            if (response instanceof Response.Success) {
                fail("Error should be called but was Success");
            }

            if (response instanceof Response.Error) {
                assertEquals(-1, ((Response.Error<Apod>) response).code);
                assertEquals(ERROR_LOADING_APOD, ((Response.Error<Apod>) response).error);
            }
        });
    }

    @Test
    public void loadApodShouldBuildTheRequestSuccessfullyWhenDateIsNotNull() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

        Date expectedDate = new Date(120, 3, 14);

        apodDataSource.loadApod(expectedDate, response -> {});

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

        apodDataSource.loadApod(null, response -> {});

        try {
            RecordedRequest request = mockWebServer.takeRequest();
            assertEquals("/planetary/apod?date=" + expectedDate + "&hd=true&api_key=DEMO_KEY", request.getPath());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class RemoteApodDataProvider {

        static final String serverResponseOk = "{\n" +
                "    \"date\": \"2020-04-14\",\n" +
                "    \"explanation\": \"NGC 253 is one of the brightest spiral galaxies visible, but also one of the dustiest.\",\n" +
                "    \"hdurl\": \"https://apod.nasa.gov/apod/image/2004/NGC253_HstSubaruEsoNew_3500.jpg\",\n" +
                "    \"media_type\": \"image\",\n" +
                "    \"service_version\": \"v1\",\n" +
                "    \"title\": \"NGC 253: The Silver Coin Galaxy\",\n" +
                "    \"url\": \"https://apod.nasa.gov/apod/image/2004/NGC253_HstSubaruEsoNew_960.jpg\"\n" +
                "}";

        static final String serverResponseErrorApiKeyMissing = "{\n" +
                "  \"error\": {\n" +
                "    \"code\": \"API_KEY_MISSING\",\n" +
                "    \"message\": \"No api_key was supplied. Get one at https://api.nasa.gov:443\"\n" +
                "  }\n" +
                "}";

        static final String serverResponseErrorWrongApiKey = "{\n" +
                "  \"error\": {\n" +
                "    \"code\": \"API_KEY_INVALID\",\n" +
                "    \"message\": \"An invalid api_key was supplied. Get one at https://api.nasa.gov:443\"\n" +
                "  }\n" +
                "}";

        static final String serverResponseErrorDateOutOfBounds = "{\n" +
                "    \"code\": 400,\n" +
                "    \"msg\": \"Date must be between Jun 16, 1995 and Apr 15, 2020.\",\n" +
                "    \"service_version\": \"v1\"\n" +
                "}";

        static final String serverResponseErrorWrongFormatDate = "{\n" +
                "    \"code\": 400,\n" +
                "    \"msg\": \"time data 'WrongFormat' does not match format '%Y-%m-%d'\",\n" +
                "    \"service_version\": \"v1\"\n" +
                "}";
    }

}