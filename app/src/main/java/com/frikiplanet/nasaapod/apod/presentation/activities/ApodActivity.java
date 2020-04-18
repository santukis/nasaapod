package com.frikiplanet.nasaapod.apod.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;

import com.frikiplanet.nasaapod.BuildConfig;
import com.frikiplanet.nasaapod.R;
import com.frikiplanet.nasaapod.apod.data.datasources.LocalApodDataSource;
import com.frikiplanet.nasaapod.apod.data.datasources.RemoteApodDataSource;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.data.repository.ApodRepository;
import com.frikiplanet.nasaapod.apod.domain.usecases.LoadApod;
import com.frikiplanet.nasaapod.apod.domain.usecases.LoadApod2;
import com.frikiplanet.nasaapod.apod.presentation.presenter.ApodPresenter;
import com.frikiplanet.nasaapod.apod.presentation.views.ApodView;
import com.frikiplanet.nasaapod.core.data.local.AppDatabase;
import com.frikiplanet.nasaapod.core.data.remote.HttpClient;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;

public class ApodActivity extends AppCompatActivity implements ApodView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);

        // 01
        ApodPresenter presenter = new ApodPresenter(
                new ApodRepository(
                        new LocalApodDataSource(AppDatabase.getInstance(this)),
                        new RemoteApodDataSource(new HttpClient(BuildConfig.HOST))),
                this);

        presenter.loadApod(new Date());

        // 02
        LoadApod loadApod = new LoadApod(new ApodRepository(
                new LocalApodDataSource(AppDatabase.getInstance(this)),
                new RemoteApodDataSource(new HttpClient(BuildConfig.HOST))),
                this);

        loadApod.execute(new Date());

        //03
        LoadApod2 loadApod2 = new LoadApod2(new ApodRepository(
                new LocalApodDataSource(AppDatabase.getInstance(this)),
                new RemoteApodDataSource(new HttpClient(BuildConfig.HOST))));

        loadApod2.execute(new Date(), response -> {
            if (response instanceof Response.Success) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    //pintar vista
                }
            }

            if (response instanceof Response.Error) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    //pintar vista
                }
            }
        });
    }

    @Override
    public void showApod(Apod apod) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            //pintar vista
        }
    }

    @Override
    public void showError(String error) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            //pintar vista
        }
    }
}
