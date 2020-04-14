package com.frikiplanet.nasaapod.core.domain;

public interface Callback<Item> {

    void execute(Item item);
}
