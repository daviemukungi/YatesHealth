package com.oneshoppoint.yates.util;

/**
 * Created by robinson on 2/3/16.
 */
public class RestException extends RuntimeException {
    private String status;
    private String message;

    public RestException(String status,String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
