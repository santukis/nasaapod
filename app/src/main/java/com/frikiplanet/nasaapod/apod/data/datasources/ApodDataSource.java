package com.frikiplanet.nasaapod.apod.data.datasources;

import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.EitherCallback;
import com.frikiplanet.nasaapod.core.domain.LegacyCallback;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;

public interface ApodDataSource {

    String ERROR_LOADING_APOD = "Unable to load Apod from DataSource";

    //01
    default Apod loadApod(Date from) {
        return Apod.EMPTY;
    }

    //02
    default void loadApod(Date from, LegacyCallback<Apod> onResult) {
        onResult.onError(ERROR_LOADING_APOD);
    }

    //03
    default void loadApod(Date from, EitherCallback<Error, Apod> onResult) {
        onResult.execute(new Error(ERROR_LOADING_APOD), Apod.EMPTY);
    }

    //04
    default void loadApod(Date from, Callback<Response<Apod>> onResult) {
        onResult.execute(new Response.Error<Apod>(ERROR_LOADING_APOD));
    }
}
