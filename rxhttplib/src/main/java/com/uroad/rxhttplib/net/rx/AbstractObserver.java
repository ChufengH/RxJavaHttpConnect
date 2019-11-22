package com.uroad.rxhttplib.net.rx;


import com.alibaba.fastjson.JSONObject;

import com.uroad.rxhttplib.net.config.RxHttpConnect;
import com.uroad.rxhttplib.net.until.FastJsonUtils;
import com.uroad.rxhttplib.net.until.JSONUtils;
import com.uroad.rxhttplib.net.until.LogUtil;
import com.uroad.rxhttplib.net.views.LatteLoader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

//观察者的封装
public abstract class AbstractObserver<T> implements Observer<String> {

    //不需要下面重写的四个方法,只需要自己写的两个抽象方法
    public abstract void onSuccess(T t);

    public abstract void onFailure(String message);


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        LogUtil.i("result", "result = " + result);
        if (RxHttpConnect.key.equals("") || RxHttpConnect.code.equals("")) { //等于空符串
            handleJson(result);
        } else {
            if (FastJsonUtils.getJsonStatuByStatus(jsonObject, RxHttpConnect.key, RxHttpConnect.code)) { //判断服务器返回数据是否正常
                handleJson(result);
            } else {
                if (FastJsonUtils.getMsg(jsonObject) != null) {
                    onFailure(FastJsonUtils.getMsg(jsonObject));
                }
                LatteLoader.stopLoading();
            }
        }

    }


    private void handleJson(String result) {
        //这里的s是请求网络传回来的string字符串
        Type type = getClass().getGenericSuperclass();
        if (type != null) {
            try {
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                Class clazz = (Class) types[0];//强转成类对象
                T t = (T) JSONUtils.parseObject(result, clazz);
                //调用抽象方法onSuccess
                onSuccess(t);
            } catch (Exception e) {
                LogUtil.i("result", "Json解析错误信息：" + e.getMessage());
                onFailure("数据解析错误");
            }
        } else {
            onFailure("数据解析错误");
        }
        LatteLoader.stopLoading();
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e.getMessage());
        LatteLoader.stopLoading();
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
