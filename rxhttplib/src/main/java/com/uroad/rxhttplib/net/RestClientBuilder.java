package com.uroad.rxhttplib.net;

import android.content.Context;


import com.uroad.rxhttplib.net.impl.IError;
import com.uroad.rxhttplib.net.impl.IFailure;
import com.uroad.rxhttplib.net.impl.IRequest;
import com.uroad.rxhttplib.net.impl.ISuccess;
import com.uroad.rxhttplib.net.views.LoaderStytle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    private String mURL;
    private static final Map<String, Object> mPAPARMS = RestCreator.getParams();
    private IRequest mIREQUEST = null;
    private ISuccess mSUCCESS = null;
    private IFailure mFAILURE = null;
    private IError mERROR = null;
    private RequestBody mBOBY = null;
    private Context mContext = null;
    private LoaderStytle mLoaderstyle = null;
    private File file = null;
    private String mDownloadDir = null;
    private String mEtension = null;
    private String mName = null;
    private String id;
    private String head;

    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        this.mURL = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        mPAPARMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        mPAPARMS.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBOBY = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess isuccess) {
        this.mSUCCESS = isuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mFAILURE = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mERROR = iError;
        return this;
    }

    public final RestClientBuilder file(String key, Object value) {
        mPAPARMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.file = new File(file);
        return this;
    }

    public final RestClientBuilder id(String id) {
        this.id = id;
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIREQUEST = iRequest;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStytle stytle) {
        this.mContext = context;
        this.mLoaderstyle = stytle;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderstyle = LoaderStytle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder extension(String me) {
        this.mEtension = me;
        return this;
    }
    public final RestClientBuilder head(String head) {
        this.head = head;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mURL, mPAPARMS, mIREQUEST, mSUCCESS, mFAILURE, mERROR, mBOBY, mContext, mLoaderstyle, file, mDownloadDir, mEtension, mName,id,head);
    }
}
