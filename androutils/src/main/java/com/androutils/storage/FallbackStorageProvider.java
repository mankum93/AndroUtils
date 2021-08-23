package com.androutils.storage;

import android.os.Environment;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FallbackStorageProvider implements StorageProvider {

    @Nullable
    @Override
    public List<File> getStorages() throws StorageRetrievalException {
        List<File> storages = new ArrayList<>();
        storages.add(Environment.getExternalStorageDirectory());
        return storages;
    }
}
