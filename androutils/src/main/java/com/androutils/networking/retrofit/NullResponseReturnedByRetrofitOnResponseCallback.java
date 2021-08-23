package com.androutils.networking.retrofit;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 08-07-2020
 */
public class NullResponseReturnedByRetrofitOnResponseCallback extends Exception {

    public NullResponseReturnedByRetrofitOnResponseCallback() {
    }

    public NullResponseReturnedByRetrofitOnResponseCallback(String message) {
        super(message);
    }

    public NullResponseReturnedByRetrofitOnResponseCallback(String message, Throwable cause) {
        super(message, cause);
    }

    public NullResponseReturnedByRetrofitOnResponseCallback(Throwable cause) {
        super(cause);
    }
}
