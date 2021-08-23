package com.androutils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import timber.log.Timber;

/**
 * A Fragment that enables logging the lifecycle methods. Simply extend it to enable.
 * Also, it must be ensured that the super() calls are maintained for the lifecycle methods in your fragment.
 */
public class LifecycleLogFragment extends Fragment {

    public static final String TAG = "FragmentLifecycle";

    @Override
    public void onAttach(Context context) {
        Timber.d( this.getClass().getSimpleName() + "." + "onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d( this.getClass().getSimpleName() + "." + "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d( this.getClass().getSimpleName() + "." + "onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Timber.d( this.getClass().getSimpleName() + "." + "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Timber.d( this.getClass().getSimpleName() + "." + "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Timber.d( this.getClass().getSimpleName() + "." + "onViewStateRestored()");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        Timber.d( this.getClass().getSimpleName() + "." + "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Timber.d( this.getClass().getSimpleName() + "." + "onResume()");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Timber.d( this.getClass().getSimpleName() + "." + "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        Timber.d( this.getClass().getSimpleName() + "." + "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Timber.d( this.getClass().getSimpleName() + "." + "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Timber.d( this.getClass().getSimpleName() + "." + "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Timber.d( this.getClass().getSimpleName() + "." + "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Timber.d( this.getClass().getSimpleName() + "." + "onDetach()");
        super.onDetach();
    }
}
