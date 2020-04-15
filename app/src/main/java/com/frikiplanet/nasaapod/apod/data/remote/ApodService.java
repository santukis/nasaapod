package com.frikiplanet.nasaapod.apod.data.remote;

import com.frikiplanet.nasaapod.apod.data.model.Apod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApodService {

    @GET("planetary/apod")
    Call<Apod> loadApod(@Query("date") String date,
                        @Query("hd") boolean hd,
                        @Query("api_key") String apiKey);

}
