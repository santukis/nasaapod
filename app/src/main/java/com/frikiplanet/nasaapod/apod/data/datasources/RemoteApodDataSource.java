package com.frikiplanet.nasaapod.apod.data.datasources;

import com.frikiplanet.nasaapod.BuildConfig;
import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.data.remote.Converters;
import com.frikiplanet.nasaapod.apod.data.remote.HttpClient;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;

import retrofit2.Call;

public class RemoteApodDataSource implements ApodDataSource {

    public HttpClient httpClient;

    public RemoteApodDataSource(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void loadApod(Date from, Callback<Response<Apod>> onResult) {
        Call<Apod> call = httpClient.apodService.loadApod(Converters.fromDateToString(from), true, BuildConfig.API_KEY);
        onResult.execute(HttpClient.unwrapCall(call));
    }
}
