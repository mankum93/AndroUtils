package com.androutils.networking.retrofit;

import retrofit2.Response;

/**
 * This class models out a
 *
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 17-10-2019
 */
public class RetrofitAPIResponse<T> {
    public Response<T> response;
    public Throwable throwable;

    public RetrofitAPIResponse(Response<T> response) {
        this.response = response;
    }

    public RetrofitAPIResponse(Throwable throwable) {
        this.throwable = throwable;
    }
}
