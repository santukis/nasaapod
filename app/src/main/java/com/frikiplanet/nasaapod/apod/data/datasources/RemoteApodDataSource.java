package com.frikiplanet.nasaapod.apod.data.datasources;

import com.frikiplanet.nasaapod.apod.data.model.Apod;
import com.frikiplanet.nasaapod.apod.data.remote.Converters;
import com.frikiplanet.nasaapod.apod.data.remote.HttpClient;
import com.frikiplanet.nasaapod.core.domain.Callback;
import com.frikiplanet.nasaapod.core.domain.Response;

import java.util.Date;

public class RemoteApodDataSource implements ApodDataSource {

    private static final String API_KEY = "DEMO_KEY";

    public HttpClient httpClient;

    public RemoteApodDataSource(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void loadApod(Date from, Callback<Response<Apod>> onResult) {
        onResult.execute(HttpClient.unwrapCall(httpClient.apodService.loadApod(new Converters().fromDateToString(from), true, API_KEY)));
    }
}
