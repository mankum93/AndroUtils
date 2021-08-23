package com.androutils.networking;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return Constants.MSG_NETWORK_CONNECTIVITY_UNAVAILABLE;
    }
}