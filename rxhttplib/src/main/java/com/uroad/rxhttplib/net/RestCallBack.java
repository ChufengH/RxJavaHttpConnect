package com.uroad.rxhttplib.net;

import android.os.Handler;


import com.uroad.rxhttplib.net.impl.IError;
import com.uroad.rxhttplib.net.impl.IFailure;
import com.uroad.rxhttplib.net.impl.IRequest;
import com.uroad.rxhttplib.net.impl.ISuccess;
import com.uroad.rxhttplib.net.until.LogUtil;
import com.uroad.rxhttplib.net.views.LatteLoader;
import com.uroad.rxhttplib.net.views.LoaderStytle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestCallBack implements Callback<String> {
    private final IRequest IREQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private  final LoaderStytle LOADER_STYLE;
    private static final Handler HANDLER = new Handler() ;
    private final String id;

    public RestCallBack(IRequest request, ISuccess success, IFailure failure, IError error,LoaderStytle loaderStytle,String id) {
        this.IREQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = loaderStytle;
        this.id = id;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body(),id);
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (IREQUEST!=null){
            IREQUEST.onRequestEnd();
        }

        stopLoading();
    }
    private void stopLoading(){
        if (LOADER_STYLE!=null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            },500);
        }
    }
}
