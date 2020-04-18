package com.frikiplanet.nasaapod.apod.domain.usecases;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;
import com.frikiplanet.nasaapod.core.domain.UseCase2;

import java.util.Date;

public class LoadApod2 extends UseCase2<Date, Apod> {

    private ApodDataSource repository;

    public LoadApod2(ApodDataSource repository) {
        this.repository = repository;
    }

    @Override
    protected void run(Date date, Callback<Response<Apod>> onResult) {
        repository.loadApod(date, onResult);
    }
}
