package com.frikiplanet.nasaapod.apod.presentation.presenter;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;

public class ApodPresenter {

    private ApodDataSource repository;

    public ApodPresenter(ApodDataSource repository) {
        this.repository = repository;
    }

    public void loadApod(Date from) {
        repository.loadApod(from, (response -> {
            if (response instanceof Response.Success) {
                //TODO SHOW APOD
            }

            if (response instanceof Response.Error) {
                //TODO SHOW ERROR
            }
        }));
    }
}
