package com.androutils.storage;

/**
 * This exception represents any general issue with retrieval of storage with a {@link StorageProvider}
 */
public class StorageRetrievalException extends Exception {

    public StorageRetrievalException(String message) {
        super(message);
    }

    public StorageRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
