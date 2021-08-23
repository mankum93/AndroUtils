package com.androutils.networking.retrofit;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An observer to map the Retrofit response/error from a bundled format: {@link RetrofitAPIResponse}
 *
 * @deprecated Use {@link RetrofitObserver2 instead}
 *
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 17-10-2019
 */
@Deprecated
public class RetrofitObserver<T> implements Observer<RetrofitAPIResponse<T>> {

    private Callback<T> retrofitCallback;

    public RetrofitObserver(Callback<T> retrofitCallback) {
        this.retrofitCallback = retrofitCallback;
    }

    @Override
    public void onChanged(@Nullable RetrofitAPIResponse<T> retrofitAPIResponse) {
        if(retrofitAPIResponse != null){
            if(retrofitAPIResponse.throwable != null){
                // Case of error
                retrofitCallback.onFailure(retrofitAPIResponse.throwable);
            }
            else{
                // Case of response
                retrofitCallback.onResponse(retrofitAPIResponse.response);
            }
        }
        else{
            // Null response from retrofit
            retrofitCallback.onFailure(new NullPointerException("Retrofit onResponse() callback returned null response"));
        }

    }

    /**
     * NOTE: This interface is taken(almost ditto) from {@link retrofit2.Callback}. The only modification
     * is the removal of {@link retrofit2.Call} param from the methods
     * ---------------------------------------------------------------------------------------------
     *
     * Communicates responses from a server or offline requests. One and only one method will be
     * invoked in response to a given request.
     * <p>
     * Callback methods are executed using the {@link Retrofit} callback executor. When none is
     * specified, the following defaults are used:
     * <ul>
     * <li>Android: Callbacks are executed on the application's main (UI) thread.</li>
     * <li>JVM: Callbacks are executed on the background thread which performed the request.</li>
     * </ul>
     *
     * @param <T> Successful response body type.
     */
    public interface Callback<T> {
        /**
         * Invoked for a received HTTP response.
         * <p>
         * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
         * Call {@link Response#isSuccessful()} to determine if the response indicates success.
         */
        void onResponse(Response<T> response);

        /**
         * Invoked when a network exception occurred talking to the server or when an unexpected
         * exception occurred creating the request or processing the response.
         */
        void onFailure(Throwable t);
    }
}
