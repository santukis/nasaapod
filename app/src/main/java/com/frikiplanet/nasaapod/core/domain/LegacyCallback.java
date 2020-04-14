package com.frikiplanet.nasaapod.core.domain;

public interface LegacyCallback<Item> {

    void onSuccess(Item item);

    void onError(String error);
}
