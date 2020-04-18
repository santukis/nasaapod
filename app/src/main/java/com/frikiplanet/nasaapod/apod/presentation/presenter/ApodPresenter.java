package com.frikiplanet.nasaapod.apod.presentation.presenter;

import android.os.Handler;
import android.os.Looper;

import com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.presentation.views.ApodView;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class ApodPresenter {

    private ApodDataSource repository;
    private ApodView view;

    private Handler handler = new Handler(Looper.getMainLooper());

    public ApodPresenter(ApodDataSource repository,
                         ApodView view) {
        this.repository = repository;
        this.view = view;
    }

    public void loadApod(Date from) {
        Executors.newSingleThreadExecutor().submit(() -> {
            repository.loadApod(from, (response -> {
                if (response instanceof Response.Success) {
                    handler.post(() -> view.showApod(((Response.Success<Apod>) response).data));
                }

                if (response instanceof Response.Error) {
                    handler.post(() -> view.showError(((Response.Error<Apod>) response).error));
                }
            }));
        });
    }

    public void loadApods() {
        Executors.newSingleThreadExecutor().submit(() -> {
            repository.loadApods(response -> {
                if (response instanceof Response.Success) {
                    handler.post(() -> view.showApods(((Response.Success<List<Apod>>) response).data));
                }
                if (response instanceof Response.Error) {
                    handler.post(() -> view.showError(((Response.Error<List<Apod>>) response).error));
                }
            });
        });
    }

    public void loadApodsBetweenDates(Date from, Date to) {
        Executors.newSingleThreadExecutor().submit(() -> {
            repository.loadApodsBetweenDates(from, to, response -> {
                if (response instanceof Response.Success) {
                    handler.post(() -> view.showApods(((Response.Success<List<Apod>>) response).data));
                }
                if (response instanceof Response.Error) {
                    handler.post(() -> view.showError(((Response.Error<List<Apod>>) response).error));
                }
            });
        });
    }

    public void deleteApod(Apod apod) {
        Executors.newSingleThreadExecutor().submit(() -> {
            repository.deleteApod(apod, (response -> {
                if (response instanceof Response.Success) {
                    handler.post(() -> view.onApodDeleted(((Response.Success<Apod>) response).data));
                }

                if (response instanceof Response.Error) {
                    handler.post(() -> view.showError(((Response.Error<Apod>) response).error));
                }
            }));
        });
    }
}
