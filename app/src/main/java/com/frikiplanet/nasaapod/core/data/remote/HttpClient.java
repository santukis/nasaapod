package com.frikiplanet.nasaapod.core.data.remote;

import com.frikiplanet.nasaapod.apod.data.remote.ApodService;
import com.frikiplanet.nasaapod.core.domain.Response;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.frikiplanet.nasaapod.apod.data.datasources.ApodDataSource.ERROR_LOADING_APOD;

public class HttpClient {

    public ApodService apodService;

    public HttpClient(String host) {
        initializeClient(host);
    }

    private void initializeClient(String host) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(
                        MoshiConverterFactory.create(
                                new Moshi.Builder().add(new Converters()).build()
                        ))
                .baseUrl(host)
                .build();

        apodService = retrofit.create(ApodService.class);
    }

    public static <Item> Response<Item> unwrapCall(Call<Item> call) {
        try {
            retrofit2.Response<Item> response = call.execute();

            if (response.isSuccessful()) {
                return new Response.Success<>(response.body());

            } else {
                JsonReader reader = JsonReader.of(response.errorBody().source());
                return new Response.Error<>(ServerErrorParser.parse(reader), response.code());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return new Response.Error<>(ERROR_LOADING_APOD);
        }
    }
}
