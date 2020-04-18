package com.frikiplanet.nasaapod.apod.domain.usecases;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.presentation.views.ApodView;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;
import com.frikiplanet.nasaapod.core.domain.UseCase;

import java.util.List;

public class LoadApods extends UseCase<Void, List<Apod>> {

    private ApodDataSource repository;
    private ApodView view;

    public LoadApods(ApodDataSource repository,
                     ApodView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    protected void run(Void aVoid, Callback<Response<List<Apod>>> onResult) {
        repository.loadApods(onResult);
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
}
