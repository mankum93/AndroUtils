package com.androutils.networking.retrofit;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 08-07-2020
 */
public class APIReturnedNullResponse extends Exception {
    public APIReturnedNullResponse() {
    }

    public APIReturnedNullResponse(String message) {
        super(message);
    }

    public APIReturnedNullResponse(String message, Throwable cause) {
        super(message, cause);
    }

    public APIReturnedNullResponse(Throwable cause) {
        super(cause);
    }
}
