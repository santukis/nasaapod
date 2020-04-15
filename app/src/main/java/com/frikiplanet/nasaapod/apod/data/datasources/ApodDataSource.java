package com.frikiplanet.nasaapod.apod.data.datasources;

import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;

public interface ApodDataSource {

    String ERROR_LOADING_APOD = "Unable to load Apod from DataSource";

    default void loadApod(Date from, Callback<Response<Apod>> onResult) {
        onResult.execute(new Response.Error<Apod>(ERROR_LOADING_APOD));
    }
}
