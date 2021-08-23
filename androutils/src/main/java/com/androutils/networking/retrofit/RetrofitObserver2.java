package com.androutils.networking.retrofit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import retrofit2.Response;
import timber.log.Timber;

/**
 * An observer to map the Retrofit response/error from a bundled format: {@link RetrofitAPIResponse}
 *
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 09-06-2020
 */
public abstract class RetrofitObserver2<T, U> extends ChainedObserver<RetrofitAPIResponse<T>, U> implements RetrofitObserver.Callback<T> {

    private static final String TAG = RetrofitObserver2.class.getSimpleName();

    public RetrofitObserver2() {
    }

    protected RetrofitObserver2(Observer<U> observerChain) {
        super(observerChain);
    }

    protected RetrofitObserver2(Observer<U> observerChain, ObserverResultMapper<RetrofitAPIResponse<T>, U> observerResultMapper) {
        super(observerChain, observerResultMapper);
    }

    @Override
    public void onChanged(@Nullable RetrofitAPIResponse<T> retrofitAPIResponse) {
        Timber.d( "Got a response from Observable...");
        if(retrofitAPIResponse != null){
            if(retrofitAPIResponse.throwable != null){
                // Case of error
                onFailure(retrofitAPIResponse.throwable);
            }
            else{
                // Case of response
                if(retrofitAPIResponse.response != null){
                    if(retrofitAPIResponse.response.body() == null){
                        onFailure(new APIReturnedNullResponse("API call returned empty body"));
                    }
                    else {
                        onResponse(retrofitAPIResponse.response);
                    }
                }
                else{
                    // Null response from retrofit
                    onFailure(new NullResponseReturnedByRetrofitOnResponseCallback("Retrofit onResponse() callback returned null response"));
                }
            }
        }
        else{
            // Null value emitted by this observable
            onFailure(new ObservableEmittedNullValue("Retrofit API Observable set with null value"));
        }
    }

    @Override
    public abstract void onResponse(@NonNull Response<T> response);
}
