package com.androutils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 03-07-2020
 */
public final class MimeUtils {

    // By default, Android doesn't provide support for JSON
    public static final String MIME_TYPE_JSON = "application/json";

    @Nullable
    public static String getMimeType(@NonNull Context context, @NonNull Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = getExtension(uri.toString());

            if (fileExtension != null) {
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                        fileExtension.toLowerCase());

                if (mimeType == null) {
                    // Handle the misc file extensions
                    mimeType = handleOtherFileExtensions(fileExtension);
                }
            }
        }
        return mimeType;
    }

    @Nullable
    private static String getExtension(@Nullable String fileName) {

        if (fileName == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        char[] arrayOfFilename = fileName.toCharArray();
        for (int i = arrayOfFilename.length - 1; i > 0; i--) {
            if (arrayOfFilename[i] == '.') {
                return fileName.substring(i + 1, fileName.length());
            }
        }
        return null;
    }

    @Nullable
    private static String handleOtherFileExtensions(@NonNull String extension) {

        if (extension.equals("json")) {
            return MIME_TYPE_JSON;
        } else {
            return null;
        }
    }
}