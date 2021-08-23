package com.androutils.networking.retrofit;

import android.content.Context;

import com.androutils.networking.NetworkingUtils;
import com.androutils.networking.NoConnectivityException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Check Network Connectivity before making the request
 */
public class NetworkConnectionInterceptor implements Interceptor {
 
    private Context mContext;
 
    public NetworkConnectionInterceptor(Context context) {
        mContext = context;
    }
 
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkingUtils.isNetworkAvailable(mContext)) {
            throw new NoConnectivityException();
            // Throwing our custom exception 'NoConnectivityException'
        }
 
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
 
}