package com.androutils.networking.retrofit;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.google.common.io.ByteStreams;
import com.androutils.MimeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 03-07-2020
 */
public final class RetrofitUtils {

    private Retrofit retrofitClient;
    private Context context;

    // Currently, APIs rely exclusively on an explicit URL
    // Base URL compulsory with Retrofit
    private static final String BASE_URL_DUMMY = "https://www.google.com";

    // In seconds
    public static final int TIMEOUT_DEFAULT = 60;

    private RetrofitUtils(OkHttpClient.Builder httpClient, Context context) {
        this.retrofitClient = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL_DUMMY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        this.context = context;
    }

    private RetrofitUtils(Context context, boolean loggingEnabled) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient .connectTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS);
        httpClient  .writeTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS);
        httpClient  .readTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS);
        if(loggingEnabled){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        this.retrofitClient = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL_DUMMY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        this.context = context;
    }

    public static RetrofitUtils with(OkHttpClient.Builder httpClient,
                                                  Context appContext) {
        return new RetrofitUtils(httpClient, appContext.getApplicationContext());
    }

    public static RetrofitUtils with(Context appContext) {
        return new RetrofitUtils(appContext.getApplicationContext(), false);
    }

    public static RetrofitUtils with(Context appContext, boolean loggingEnabled) {
        return new RetrofitUtils(appContext.getApplicationContext(), loggingEnabled);
    }

    /**
     * Prepares a {@link Call} request for content upload
     *
     * <h5>Accepts the following URI schemes:</h5>
     * <ul>
     * <li>content ({@link android.content.ContentResolver#SCHEME_CONTENT})</li>
     * <li>android.resource ({@link android.content.ContentResolver#SCHEME_ANDROID_RESOURCE})</li>
     * <li>file ({@link android.content.ContentResolver#SCHEME_FILE})</li>
     * </ul>
     *
     * @param url The HTTP request URL for content upload
     * @param contentRequestKey The key or post param key for the content upload request
     * @param contentUri Content URI
     * @param requestParams POST params to be sent with the request(optional)
     * @return A Retrofit {@link Call} object for content upload
     *
     * @throws IOException In case, problems with IO handles
     * @throws FileNotFoundException
     */
    public Call<ResponseBody> request(@NonNull String url,
                                      @NonNull String contentRequestKey,
                                      @NonNull Uri contentUri,
                                      @Nullable Map<String, String> requestParams) throws IOException, FileNotFoundException {

        InputStream contentInputStream = null;

        contentInputStream = context.getContentResolver().openInputStream(contentUri);
        if (contentInputStream == null) {
            throw new IOException("There was a problem reading the content specified by the content Uri your provided");
        }

        String contentMimeType = MimeUtils.getMimeType(context, contentUri);
        if (contentMimeType == null) {
            // Docs for getType() above say,
            // "A MIME type for the content, or null if the URL is invalid or the type is unknown"
            throw new IOException("File MIME Type cannot be determined for the given content: " + contentUri.toString());
        }

        // Content RequestBody
        RequestBody contentRequestBody = RequestBody.create(MediaType.parse(contentMimeType), ByteStreams.toByteArray(contentInputStream));
        contentInputStream.close();

        // MultipartBody.Part is used to send also the actual content "name"
        MultipartBody.Part contentBody =
                MultipartBody.Part.createFormData(contentRequestKey, contentUri.toString(), contentRequestBody);

        Map<String, RequestBody> requestBodyMap = null;
        if(requestParams != null){
            requestBodyMap = fromRequestParams(requestParams);
        }

        // Description
        /*RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
        requestBodyMap.put("description", description);*/

        // Request
        Call<ResponseBody> call;

        final FileUploadService fileUploadClient = retrofitClient.create(FileUploadService.class);
        if(requestBodyMap != null){
            // PartMap request
            call = fileUploadClient.uploadPartMap(url, requestBodyMap, contentBody);
        }
        else {
            call = fileUploadClient.upload(url, contentBody);
        }

        return call;
    }

    public Call<ResponseBody> request(@NonNull String url,
                                      @NonNull String fileRequestKey,
                                      @NonNull File file,
                                      @Nullable Map<String, String> requestParams) throws IOException, FileNotFoundException {

        // TODO:
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        if(!file.exists()){
            throw new FileNotFoundException("File you are tyring to upload does not exist!");
        }

        String fileType = MimeUtils.getMimeType(context, Uri.fromFile(file));
        if (fileType == null) {
            // Docs for getType() above say,
            // "A MIME type for the content, or null if the URL is invalid or the type is unknown"
            throw new IOException("File MIME Type cannot be determined or invalid file URL for the File: " + file.toString());
        }

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(fileType), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part fileBody =
                MultipartBody.Part.createFormData(fileRequestKey, file.getName(), requestFile);

        Map<String, RequestBody> requestBodyMap = null;
        if(requestParams != null){
            requestBodyMap = fromRequestParams(requestParams);
        }

        // Description
        /*RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
        requestBodyMap.put("description", description);*/

        // Request
        Call<ResponseBody> call;

        final FileUploadService fileUploadClient = retrofitClient.create(FileUploadService.class);
        if(requestBodyMap != null){
            // PartMap request
            call = fileUploadClient.uploadPartMap(url, requestBodyMap, fileBody);
        }
        else {
            call = fileUploadClient.upload(url, fileBody);
        }

        return call;
    }

    private Map<String, RequestBody> fromRequestParams(@NonNull Map<String, String> requestParams) {
        ArrayMap<String, RequestBody> requestBodyMap = new ArrayMap<>();
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            requestBodyMap.put(param.getKey(), RequestBody.create(okhttp3.MultipartBody.FORM, param.getValue()));
        }
        return requestBodyMap;
    }

}
