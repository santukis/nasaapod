package com.frikiplanet.nasaapod.core.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

public abstract class UseCase<Params, Result> {

    private Handler handler = new Handler(Looper.getMainLooper());

    protected abstract void run(Params params, Callback<Response<Result>> onResult);

    protected abstract void submitResponse(Response<Result> response);

    public void execute(Params params) {
        Executors.newSingleThreadExecutor().submit(() -> {
            run(params, response -> handler.post(() -> submitResponse(response)));
        });
    }
}
