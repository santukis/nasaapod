package com.frikiplanet.nasaapod.apod.data.repository;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;
import java.util.List;

public class ApodRepository implements ApodDataSource {

    private ApodDataSource localApodDataSource;
    private ApodDataSource remoteApodDataSource;

    public ApodRepository(ApodDataSource localApodDataSource,
                          ApodDataSource remoteApodDataSource) {
        this.localApodDataSource = localApodDataSource;
        this.remoteApodDataSource = remoteApodDataSource;
    }

    @Override
    public void loadApods(Callback<Response<List<Apod>>> onResult) {
        localApodDataSource.loadApods(onResult);
    }

    @Override
    public void loadApodsBetweenDates(Date from, Date to, Callback<Response<List<Apod>>> onResult) {
        localApodDataSource.loadApodsBetweenDates(from, to, onResult);
    }

    @Override
    public void saveApod(Apod apod, Callback<Response<Apod>> onResult) {
        localApodDataSource.saveApod(apod, onResult);
    }

    @Override
    public void deleteApod(Apod apod, Callback<Response<Apod>> onResult) {
        localApodDataSource.deleteApod(apod, onResult);
    }

    @Override
    public void loadApod(Date from, Callback<Response<Apod>> onResult) {
        localApodDataSource.loadApod(from, response -> {
            if (response instanceof Response.Error) {
                loadApodFromRemote(from, onResult);
            } else {
                onResult.execute(response);
            }
        });
    }

    private void loadApodFromRemote(Date from, Callback<Response<Apod>> onResult) {
        remoteApodDataSource.loadApod(from, response -> {
            if (response instanceof Response.Success) {
                saveApod(((Response.Success<Apod>) response).data, onResult);
            } else {
                onResult.execute(response);
            }
        });
    }
}
