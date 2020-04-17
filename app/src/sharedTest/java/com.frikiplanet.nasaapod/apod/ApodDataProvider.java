package com.frikiplanet.nasaapod.apod;

import com.frikiplanet.nasaapod.apod.data.model.Apod;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ApodDataProvider {

    public static final String serverResponseOk = "{\n" +
            "    \"date\": \"2020-04-14\",\n" +
            "    \"explanation\": \"NGC 253 is one of the brightest spiral galaxies visible, but also one of the dustiest.\",\n" +
            "    \"hdurl\": \"https://apod.nasa.gov/apod/image/2004/NGC253_HstSubaruEsoNew_3500.jpg\",\n" +
            "    \"media_type\": \"image\",\n" +
            "    \"service_version\": \"v1\",\n" +
            "    \"title\": \"NGC 253: The Silver Coin Galaxy\",\n" +
            "    \"url\": \"https://apod.nasa.gov/apod/image/2004/NGC253_HstSubaruEsoNew_960.jpg\"\n" +
            "}";

    public static final String serverResponseErrorApiKeyMissing = "{\n" +
            "  \"error\": {\n" +
            "    \"code\": \"API_KEY_MISSING\",\n" +
            "    \"message\": \"No api_key was supplied. Get one at https://api.nasa.gov:443\"\n" +
            "  }\n" +
            "}";

    public static final String serverResponseErrorWrongApiKey = "{\n" +
            "  \"error\": {\n" +
            "    \"code\": \"API_KEY_INVALID\",\n" +
            "    \"message\": \"An invalid api_key was supplied. Get one at https://api.nasa.gov:443\"\n" +
            "  }\n" +
            "}";

    public static final String serverResponseErrorDateOutOfBounds = "{\n" +
            "    \"code\": 400,\n" +
            "    \"msg\": \"Date must be between Jun 16, 1995 and Apr 15, 2020.\",\n" +
            "    \"service_version\": \"v1\"\n" +
            "}";

    public static final String serverResponseErrorWrongFormatDate = "{\n" +
            "    \"code\": 400,\n" +
            "    \"msg\": \"time data 'WrongFormat' does not match format '%Y-%m-%d'\",\n" +
            "    \"service_version\": \"v1\"\n" +
            "}";

    public static Date today = new Date();
    public static Date _2020_01_01 = new Date(1577833200000L);
    public static Date _2020_02_01 = new Date(1580511600000L);
    public static Date _2020_03_01 = new Date(1583017200000L);
    public static Date _NOT_STORED = new Date(1267398000000L);
    public static Date _LOWER_OUT_OF_BOUNDS = new Date(1425164400000L);
    public static Date _UPPER_OUT_OF_BOUNDS = new Date(1430431200000L);

    public static List<Apod> apods = Arrays.asList(
            new Apod(_2020_01_01),
            new Apod(_2020_02_01),
            new Apod(_2020_03_01),
            new Apod(today)
    );
}
