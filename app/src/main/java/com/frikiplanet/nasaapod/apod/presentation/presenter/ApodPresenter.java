package com.frikiplanet.nasaapod.apod.presentation.presenter;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.domain.EitherCallback;
import com.frikiplanet.nasaapod.core.domain.LegacyCallback;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;

public class ApodPresenter {

    private ApodDataSource repository;

    public ApodPresenter(ApodDataSource repository) {
        this.repository = repository;
    }

    //01
    public void loadApodSync(Date from) {
        Apod apod = repository.loadApod(from);

        if (apod == Apod.EMPTY) {
            //TODO SHOW ERROR
        } else {
            //TODO SHOW APOD
        }
    }

    //02
    public void loadApodWithLegacyCallback(Date from) {
        repository.loadApod(from, new LegacyCallback<Apod>() {
            @Override
            public void onSuccess(Apod apod) {
                //TODO SHOW APOD
            }

            @Override
            public void onError(String error) {
                //TODO SHOW ERROR
            }
        });
    }

    //03
    public void loadApodWithEitherCallback(Date from) {
        repository.loadApod(from, ((error, apod) -> {
            if (error == null) {
                //TODO SHOW ERROR
            }

            if (apod != Apod.EMPTY) {
                //TODO SHOW APOD
            }
        }));
    }

    //04
    public void loadApod(Date from) {
        repository.loadApod(from, (response -> {
            if (response instanceof Response.Success) {
                //TODO SHOW APOD
                Apod apod = ((Response.Success<Apod>) response).data;
            }

            if (response instanceof Response.Error) {
                //TODO SHOW ERROR
                String error = ((Response.Error<Apod>) response).error;
                int code = ((Response.Error<Apod>) response).code;
            }
        }));
    }
}
