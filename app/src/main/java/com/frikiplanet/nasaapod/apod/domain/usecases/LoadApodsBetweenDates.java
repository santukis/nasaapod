package com.frikiplanet.nasaapod.apod.domain.usecases;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.presentation.views.ApodView;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;
import com.frikiplanet.nasaapod.core.domain.UseCase;

import java.util.Date;
import java.util.List;

public class LoadApodsBetweenDates extends UseCase<LoadApodsBetweenDates.Params, List<Apod>> {

    private ApodDataSource repository;
    private ApodView view;

    public LoadApodsBetweenDates(ApodDataSource repository,
                                 ApodView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    protected void run(Params params, Callback<Response<List<Apod>>> onResult) {
        repository.loadApodsBetweenDates(params.from, params.to, onResult);
    }

    @Override
    protected void submitResponse(Response<List<Apod>> response) {
        if (response instanceof Response.Success) {
            view.showApods(((Response.Success<List<Apod>>) response).data);
        }

        if (response instanceof Response.Error) {
            view.showError(((Response.Error<List<Apod>>) response).error);
        }
    }

    static class Params {
        Date from;
        Date to;

        public Params(Date from, Date to) {
            this.from = from;
            this.to = to;
        }
    }
}
