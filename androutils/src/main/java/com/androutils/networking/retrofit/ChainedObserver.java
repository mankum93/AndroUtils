package com.androutils.networking.retrofit;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 21-06-2020
 */
public abstract class ChainedObserver<T, U> implements Observer<T> {

    protected Observer<U> observerChain;
    protected ObserverResultMapper<T, U> observerResultMapper;

    public ChainedObserver() {
    }

    protected ChainedObserver(Observer<U> observerChain) {
        this.observerChain = observerChain;
    }

    protected ChainedObserver(Observer<U> observerChain, ObserverResultMapper<T, U> observerResultMapper) {
        this.observerChain = observerChain;
        this.observerResultMapper = observerResultMapper;
    }

    @Override
    public void onChanged(@Nullable T t) {
        if(this.observerChain != null){
            this.observerChain.onChanged(observerResultMapper == null ? null : observerResultMapper.map(t));
        }
    }

    public static interface ObserverResultMapper<FROM, TO>{
        TO map(FROM from);

        // TODO(Manish): Could create problems as JAVA_V_8 is involved
        public static <RESULT> ObserverResultMapper<RESULT, RESULT> passAsIs(){
            return new ObserverResultMapper<RESULT, RESULT>() {
                @Override
                public RESULT map(RESULT result) {
                    return result;
                }
            };
        }
    }
}
