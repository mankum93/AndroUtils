package com.androutils;

import android.view.View;

import androidx.annotation.NonNull;

public final class ViewUtils {

    public static void toggleViewsVisibility(int newVisibility, @NonNull View... views){
        for(View view : views){
            view.setVisibility(newVisibility);
        }
    }
}
