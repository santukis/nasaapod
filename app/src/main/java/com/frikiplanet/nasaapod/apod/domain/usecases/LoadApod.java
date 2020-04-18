package com.frikiplanet.nasaapod.apod.domain.usecases;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.presentation.views.ApodView;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;
import com.frikiplanet.nasaapod.core.domain.UseCase;

import java.util.Date;

public class LoadApod extends UseCase<Date, Apod> {

    private ApodDataSource repository;
    private ApodView view;

    public LoadApod(ApodDataSource repository,
                    ApodView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    protected void run(Date date, Callback<Response<Apod>> onResult) {
        repository.loadApod(date, onResult);
    }

    @Override
    protected void submitResponse(Response<Apod> response) {
        if (response instanceof Response.Success) {
            view.showApod(((Response.Success<Apod>) response).data);
        }

        if (response instanceof Response.Error) {
            view.showError(((Response.Error<Apod>) response).error);
        }
    }
}
