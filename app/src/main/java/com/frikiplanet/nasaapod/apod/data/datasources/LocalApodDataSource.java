package com.frikiplanet.nasaapod.apod.data.datasources;

import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.core.data.local.AppDatabase;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;
import java.util.List;

public class LocalApodDataSource implements ApodDataSource {

    private AppDatabase database;

    public LocalApodDataSource(AppDatabase database) {
        this.database = database;
    }

    @Override
    public void loadApod(Date from, Callback<Response<Apod>> onResult) {
        Apod apod = database.apodDao().loadApod(from);

        if (apod == null) {
            ApodDataSource.super.loadApod(from, onResult);

        } else {
            onResult.execute(new Response.Success<>(apod));
        }
    }

    @Override
    public void saveApod(Apod apod, Callback<Response<Apod>> onResult) {
        try {
            if (database.apodDao().saveApod(apod) > 0) {
                onResult.execute(new Response.Success<>(apod));

            } else {
                ApodDataSource.super.saveApod(apod, onResult);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            ApodDataSource.super.saveApod(apod, onResult);
        }
    }

    @Override
    public void loadApods(Callback<Response<List<Apod>>> onResult) {
        List<Apod> apods = database.apodDao().loadApods();

        if (apods.isEmpty()) {
            ApodDataSource.super.loadApods(onResult);

        } else {
            onResult.execute(new Response.Success<>(apods));
        }
    }

    @Override
    public void loadApodsBetweenDates(Date from, Date to, Callback<Response<List<Apod>>> onResult) {
        List<Apod> apods = database.apodDao().loadApodsBetweenDates(from, to);

        if (apods.isEmpty()) {
            ApodDataSource.super.loadApodsBetweenDates(from, to, onResult);

        } else {
            onResult.execute(new Response.Success<>(apods));
        }
    }

    @Override
    public void deleteApod(Apod apod, Callback<Response<Apod>> onResult) {
        try {
            if (database.apodDao().deleteApod(apod) > 0) {
                onResult.execute(new Response.Success<>(apod));

            } else {
                ApodDataSource.super.deleteApod(apod, onResult);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            ApodDataSource.super.deleteApod(apod, onResult);
        }
    }
}