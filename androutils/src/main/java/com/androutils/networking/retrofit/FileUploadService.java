package com.androutils.networking.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

interface FileUploadService {

        @Multipart
        @POST
        Call<ResponseBody> upload(
                @Url String url,
                @Part MultipartBody.Part file
        );

        @Multipart
        @POST
        Call<ResponseBody> upload(
                @Url String url,
                @Part("description") RequestBody description,
                @Part MultipartBody.Part file
        );

        @Multipart
        @POST
        Call<ResponseBody> uploadPartMap(
                @Url String url,
                @PartMap() Map<String, RequestBody> partMap,
                @Part MultipartBody.Part file
        );
    }