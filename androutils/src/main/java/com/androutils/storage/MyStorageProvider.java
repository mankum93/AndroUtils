package com.androutils.storage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androutils.storage.android.ReflectiveStorageManagerStorageProvider;

import java.io.File;
import java.util.List;

public class MyStorageProvider implements StorageProvider {

    private Context context;

    public MyStorageProvider(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @Nullable
    @Override
    public List<File> getStorages() throws StorageRetrievalException {
        StorageProvider mainProvider = new ReflectiveStorageManagerStorageProvider(this.context);
        List<File> storages = mainProvider.getStorages();
        if(storages == null || storages.isEmpty()){
            // Fallback
            storages = new FallbackStorageProvider().getStorages();
        }
        return storages;
    }
}
