package com.uroad.rxhttplib.net.impl;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Description: 请求接口
 * @Author: chufeng
 * @CreateDate: 2019/4/21 16:54
 */
public interface RestService {
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);
    @FormUrlEncoded
    @POST
    Call<String> postByHeader(@Url String url, @FieldMap Map<String, Object> params, @Header("Authorization") String header);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);
    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    @Streaming //一边下载 一边写入 防止内存溢出
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    //Post 分步请求上传
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);


}
