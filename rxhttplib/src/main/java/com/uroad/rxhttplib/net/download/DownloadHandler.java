package com.uroad.rxhttplib.net.download;

import android.content.Context;
import android.os.AsyncTask;


import com.uroad.rxhttplib.net.RestCreator;
import com.uroad.rxhttplib.net.impl.IError;
import com.uroad.rxhttplib.net.impl.IFailure;
import com.uroad.rxhttplib.net.impl.IRequest;
import com.uroad.rxhttplib.net.impl.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {
    private final String URL;
    private static final WeakHashMap<String, Object> PAPARMS = RestCreator.getParams();
    private final IRequest IREQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String Name;
    private final String id;
    private final Context context;

    public DownloadHandler(String url, IRequest request,
                           String DOWNLOAD_DIR, String EXTENSION,
                           String name, ISuccess success, IFailure failure, IError error, String id, Context context) {
        this.URL = url;
        this.IREQUEST = request;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.Name = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.id =id;
        this.context = context;
    }

    public final void handleDownload() {
        if (IREQUEST != null) {
            IREQUEST.onRequestStart();
        }
        RestCreator.geRestSevice().download(URL, PAPARMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody body = response.body();
                            final SaveFileTask task = new SaveFileTask(IREQUEST, SUCCESS,id,context);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, body, Name);

                            //否则下载不安全
                            if (task.isCancelled()) {
                                if (IREQUEST != null) {
                                    IREQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE==null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
