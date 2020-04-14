package com.frikiplanet.nasaapod.core.domain;

public interface EitherCallback<Error, Success> {

    void execute(Error error, Success success);
}
