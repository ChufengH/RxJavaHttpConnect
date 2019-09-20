package com.uroad.rxhttplib.net.rx;

import android.content.Context;


import com.uroad.rxhttplib.net.RestCreator;
import com.uroad.rxhttplib.net.views.LoaderStytle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RxRestClientBuilder {
    private String mURL;
    private static final Map<String, Object> mPAPARMS = RestCreator.getParams();
    private RequestBody mBOBY = null;
    private Context mContext = null;
    private LoaderStytle mLoaderstyle = null;
    private File file = null;
    private String id;

    RxRestClientBuilder() {

    }

    public final RxRestClientBuilder url(String url) {
        this.mURL = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        mPAPARMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        mPAPARMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBOBY = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    public final RxRestClientBuilder file(String key, Object value) {
        mPAPARMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder file(String file) {
        this.file = new File(file);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStytle stytle) {
        this.mContext = context;
        this.mLoaderstyle = stytle;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderstyle = LoaderStytle.PacmanIndicator;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mURL, mPAPARMS ,mBOBY, mContext, mLoaderstyle, file);
    }
}
