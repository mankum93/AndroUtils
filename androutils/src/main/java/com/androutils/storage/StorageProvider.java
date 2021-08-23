package com.androutils.storage;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.List;

/**
 * Android doesn't provide any standardized way of accessing all the Storage volumes(emulated storages,
 * all the external SD cards, etc.) for API < 24. This interface serves as a contract for impl.
 * of a strategy to get the same.
 */
public interface StorageProvider {

    @Nullable
    public List<File> getStorages() throws StorageRetrievalException;
}
