package com.frikiplanet.nasaapod.apod.domain.usecases;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.presentation.views.ApodView;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;
import com.frikiplanet.nasaapod.core.domain.UseCase;

import java.util.List;

public class DeleteApod extends UseCase<Apod, Apod> {

    private ApodDataSource repository;
    private ApodView view;

    public DeleteApod(ApodDataSource repository,
                      ApodView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    protected void run(Apod apod, Callback<Response<Apod>> onResult) {
        repository.deleteApod(apod, onResult);
    }

    @Override
    protected void submitResponse(Response<Apod> response) {
        if (response instanceof Response.Success) {
            view.onApodDeleted(((Response.Success<Apod>) response).data);
        }

        if (response instanceof Response.Error) {
            view.showError(((Response.Error<Apod>) response).error);
        }
    }
}
