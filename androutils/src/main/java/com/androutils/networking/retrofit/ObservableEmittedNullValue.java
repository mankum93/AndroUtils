package com.androutils.networking.retrofit;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 08-07-2020
 */
public class ObservableEmittedNullValue extends Exception {

    public ObservableEmittedNullValue() {
    }

    public ObservableEmittedNullValue(String message) {
        super(message);
    }

    public ObservableEmittedNullValue(String message, Throwable cause) {
        super(message, cause);
    }

    public ObservableEmittedNullValue(Throwable cause) {
        super(cause);
    }
}
