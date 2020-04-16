package com.frikiplanet.nasaapod.apod.data.datasources;

import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;
import java.util.List;

public interface ApodDataSource {

    String ERROR_LOADING_APOD = "Unable to load Apod from DataSource";
    String ERROR_SAVING_APOD = "Unable to save Apod within DataSource";
    String ERROR_DELETING_APOD = "Unable to delete Apod from DataSource";

    default void loadApod(Date from, Callback<Response<Apod>> onResult) {
        onResult.execute(new Response.Error<Apod>(ERROR_LOADING_APOD));
    }

    default void saveApod(Apod apod, Callback<Response<Apod>> onResult) {
        onResult.execute(new Response.Error<>(ERROR_SAVING_APOD));
    }

    default void loadApods(Callback<Response<List<Apod>>> onResult) {
        onResult.execute(new Response.Error<>(ERROR_LOADING_APOD));
    }

    default void loadApodsBetweenDates(Date from, Date to, Callback<Response<List<Apod>>> onResult) {
        onResult.execute(new Response.Error<>(ERROR_LOADING_APOD));
    }

    default void deleteApod(Apod apod, Callback<Response<Apod>> onResult) {
        onResult.execute(new Response.Error<>(ERROR_DELETING_APOD));
    }
}
