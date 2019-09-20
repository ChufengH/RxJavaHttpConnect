package com.uroad.rxhttplib.net;



import com.uroad.rxhttplib.net.config.Config;
import com.uroad.rxhttplib.net.impl.RestService;
import com.uroad.rxhttplib.net.rx.RxRestService;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @Description: retrofit初始化默认配置
 * @Author: chufeng
 * @CreateDate: 2019/4/21 19:59
 */
public  class RestCreator {
    private static final  String TAG ="httpInterceptorMessage";
   private static final class ParamsHolder{
       public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }
    public static WeakHashMap<String,Object> getParams(){
       return ParamsHolder.PARAMS;
    }


    public static RestService geRestSevice(){
        return RestServiceHolder.REST_SERVICE;
    }
    private static final class RetrofitHolder {
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder().baseUrl(Config.base_url)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    private static final class OkHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.SECONDS).addInterceptor(new LoggerInterceptor(TAG)).build();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE
                = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE
                = RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }
    public static RxRestService getRxRestSevice(){
        return RxRestServiceHolder.REST_SERVICE;
    }

}
