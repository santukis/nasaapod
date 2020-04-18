package com.frikiplanet.nasaapod.core.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

public abstract class UseCase2<Params, Result> {

    private Handler handler = new Handler(Looper.getMainLooper());

    protected abstract void run(Params params, Callback<Response<Result>> onResult);

    public void execute(Params params, Callback<Response<Result>> onResult) {
        Executors.newSingleThreadExecutor().submit(() -> {
            run(params, response -> handler.post(() -> onResult.execute(response)));
        });
    }
}
