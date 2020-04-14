package com.frikiplanet.nasaapod.core.domain;

public class Response<Item> {

    private Response() { }

    public final static class Success<Item> extends Response<Item> {
        public Item data;

        public Success(Item data) {
            this.data = data;
        }
    }

    public final static class Error<Item> extends Response<Item> {
        public String error;
        public int code;

        public Error(String error) {
            this(error, -1);
        }

        public Error(String error, int code) {
            this.error = error;
            this.code = code;
        }
    }
}
