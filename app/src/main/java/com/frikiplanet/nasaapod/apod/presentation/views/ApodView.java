package com.frikiplanet.nasaapod.apod.presentation.views;

import com.frikiplanet.nasaapod.apod.data.model.Apod;

import java.util.List;

public interface ApodView {

    default void showApod(Apod apod) {

    }

    default void showApods(List<Apod> apods) {

    }

    default void onApodDeleted(Apod apod) {

    }

    default void showError(String error) {

    }
}
