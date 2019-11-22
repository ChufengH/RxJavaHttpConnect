package com.uroad.rxhttplib.net.rx;

import android.content.Context;

import com.uroad.rxhttplib.net.HttpMethod;
import com.uroad.rxhttplib.net.RestCreator;
import com.uroad.rxhttplib.net.views.LatteLoader;
import com.uroad.rxhttplib.net.views.LoaderStytle;


import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * @Description: 请求中心
 * @Author: chufeng
 * @CreateDate: 2019/4/21 16:53
 */
public class RxRestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PAPARMS = RestCreator.getParams();
    private final RequestBody BOBY;
    private final LoaderStytle LOADER_STYLE;
    private final File file;
    private final Context CONTEXT;



    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        Context context,
                        LoaderStytle loaderStytle,
                        File file
                       ) {
        this.URL = url;
        PAPARMS.putAll(params);
        this.BOBY = body;
        this.CONTEXT =context;
        this.LOADER_STYLE =loaderStytle;
        this.file=file;


    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestSevice(); //相当于一下子获取了retrofit默认请求配置
      Observable<String> observable =null;
        if (LOADER_STYLE!=null&&CONTEXT!=null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        switch (method) {
            case GET:
                observable = service.get(URL,PAPARMS);
                break;
            case PUT:
                observable = service.put(URL,PAPARMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BOBY);
                break;
            case POST_RAW:
                observable = service.postRaw(URL,BOBY);
                break;
            case POST:
                observable = service.post(URL,PAPARMS);
                break;
            case DELETE:
                observable = service.delete(URL,PAPARMS);
                break;
            case UOLOAD:
            final RequestBody requestBody =RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),file);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
                observable = RestCreator.getRxRestSevice().upload(URL,body);
                break;
            default:
                break;
        }
      return observable;
    }

    public final Observable<String>  get(){
      return   request(HttpMethod.GET);
    }
    public final Observable<String>  post(){
        if (BOBY==null){
         return    request(HttpMethod.POST);
        }else {
            if (!PAPARMS.isEmpty()){
                throw new RuntimeException("params must be null..");
            }
        return     request(HttpMethod.POST_RAW);
        }
    }
    public final Observable<String>  put(){

        if (BOBY==null){
          return   request(HttpMethod.PUT);
        }else {
            if (!PAPARMS.isEmpty()){
                throw new RuntimeException("params must be null..");
            }
          return   request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String>  delete(){
      return   request(HttpMethod.DELETE);
    }
    public final Observable<String>  upload(){
        return   request(HttpMethod.UOLOAD);
    }
    public final Observable<ResponseBody>  download(){
      final  Observable<ResponseBody> responseBodyObservable = RestCreator.getRxRestSevice().download(URL,PAPARMS);
      return  responseBodyObservable;
    }

}
