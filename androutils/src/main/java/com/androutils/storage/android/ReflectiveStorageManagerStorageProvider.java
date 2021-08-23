package com.androutils.storage.android;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import androidx.annotation.NonNull;

import com.androutils.storage.StorageProvider;
import com.androutils.storage.StorageRetrievalException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveStorageManagerStorageProvider implements StorageProvider {

    private static final String TAG = "RefStorageProvider";
    private Context context;

    public ReflectiveStorageManagerStorageProvider(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public List<File> getStorages() throws StorageRetrievalException {

        List<File> storages = null;
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            String[] paths = (String[]) storageManager.getClass().getMethod("getVolumePaths").invoke(storageManager);

            storages = new ArrayList<>(paths.length);

            for (int i = 0; i < paths.length; i++) {
                String status = (String) storageManager.getClass().getMethod("getVolumeState", String.class).invoke(storageManager, paths[i]);
                if (Environment.MEDIA_MOUNTED.equals(status)) {
                    File f = new File(paths[i]);
                    storages.add(f);
                }
            }
        } catch (Throwable e) {
            //XLog.tag(TAG).e("There was a problem with creation of output files for data", e);
            throw new StorageRetrievalException("There was a problem with creation of output files for data", e);
        }

        return storages;
    }
}
