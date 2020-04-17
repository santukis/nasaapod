package com.frikiplanet.nasaapod.apod.data.repository;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public class ApodRepositoryUnitTest {

    @Mock
    private ApodDataSource localApodDataSource;

    @Mock
    private ApodDataSource remoteApodDataSource;

    private ApodDataSource apodRepository;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        apodRepository = new ApodRepository(localApodDataSource, remoteApodDataSource);
    }

    @Test
    public void loadApodShouldReturnSuccesWhenApodIsStoredInLocal() {
        Date desiredDate = new Date();
        Apod expectedApod = new Apod(desiredDate);

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Success<>(expectedApod));
            return null;
        }).when(localApodDataSource).loadApod(any(Date.class), any(Callback.class));

        apodRepository.loadApod(desiredDate, response -> {
            if (response instanceof Response.Success) {
                assertEquals(expectedApod, ((Response.Success<Apod>) response).data);

            } else {
                fail("Success should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenApodIsNotStoredInLocalAndRemote() {
        Date desiredDate = new Date();
        String expectedError = "Expected Error";

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Error<>("Unreachable error"));
            return null;
        }).when(localApodDataSource).loadApod(any(Date.class), any(Callback.class));

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Error<>(expectedError));
            return null;
        }).when(remoteApodDataSource).loadApod(any(Date.class), any(Callback.class));

        apodRepository.loadApod(desiredDate, response -> {
            if (response instanceof Response.Error) {
                assertEquals(expectedError, ((Response.Error<Apod>) response).error);

            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnErrorWhenApodCantBeStoredInLocal() {
        Date desiredDate = new Date();
        String expectedError = "Expected Error";

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Error<>("Unreachable error"));
            return null;
        }).when(localApodDataSource).loadApod(any(Date.class), any(Callback.class));

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Success<>(new Apod()));
            return null;
        }).when(remoteApodDataSource).loadApod(any(Date.class), any(Callback.class));

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Error<>(expectedError));
            return null;
        }).when(localApodDataSource).saveApod(any(Apod.class), any(Callback.class));

        apodRepository.loadApod(desiredDate, response -> {
            if (response instanceof Response.Error) {
                assertEquals(expectedError, ((Response.Error<Apod>) response).error);

            } else {
                fail("Error should be called");
            }
        });
    }

    @Test
    public void loadApodShouldReturnSuccessWhenApodIsReturnedFromWebServiceAndSuccessfullyStoredInDatabase() {
        Date desiredDate = new Date();
        Apod expectedApod = new Apod(desiredDate);

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Error<>("Unreachable error"));
            return null;
        }).when(localApodDataSource).loadApod(any(Date.class), any(Callback.class));

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Success<>(expectedApod));
            return null;
        }).when(remoteApodDataSource).loadApod(any(Date.class), any(Callback.class));

        doAnswer( invocation -> {
            ((Callback<Response<Apod>>) invocation.getArguments()[1]).execute(new Response.Success<>(expectedApod));
            return null;
        }).when(localApodDataSource).saveApod(any(Apod.class), any(Callback.class));

        apodRepository.loadApod(desiredDate, response -> {
            if (response instanceof Response.Success) {
                assertEquals(expectedApod, ((Response.Success<Apod>) response).data);

            } else {
                fail("Success should be called");
            }
        });
    }

}