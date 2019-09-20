package com.uroad.rxhttplib.net;

import android.content.Context;


import com.uroad.rxhttplib.net.download.DownloadHandler;
import com.uroad.rxhttplib.net.impl.IError;
import com.uroad.rxhttplib.net.impl.IFailure;
import com.uroad.rxhttplib.net.impl.IRequest;
import com.uroad.rxhttplib.net.impl.ISuccess;
import com.uroad.rxhttplib.net.impl.RestService;
import com.uroad.rxhttplib.net.views.LatteLoader;
import com.uroad.rxhttplib.net.views.LoaderStytle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @Description: 请求中心
 * @Author: chufeng
 * @CreateDate: 2019/4/21 16:53
 */
public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PAPARMS = RestCreator.getParams();
    private final IRequest IREQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BOBY;
    private final LoaderStytle LOADER_STYLE;
    private final File file;
    private final Context CONTEXT;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String Name;
    private final String id;
    private final String head;
    private Call<String> call = null;

    public RestClient(String url,
                      Map<String, Object> params,
                      IRequest request, ISuccess success,
                      IFailure failure, IError error,
                      RequestBody body,
                      Context context,
                      LoaderStytle loaderStytle,
                      File file,
                      String downloadDir, String extension, String name, String id,String head) {
        this.URL = url;
        PAPARMS.putAll(params);
        this.IREQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BOBY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStytle;
        this.file = file;
        this.Name = name;
        this.head =head;
        this.EXTENSION = extension;
        this.DOWNLOAD_DIR = downloadDir;
        this.id = id;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.geRestSevice(); //相当于一下子获取了retrofit默认请求配置
        if (IREQUEST != null) {
            IREQUEST.onRequestStart();
        }
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                call = service.get(URL, PAPARMS);
                break;
            case PUT:
                call = service.put(URL, PAPARMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BOBY);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BOBY);
                break;
            case POST:
                call = service.post(URL, PAPARMS);
                break;
            case POST_HEADERS:
                call = service.postByHeader(URL,PAPARMS,head);
                break;
            case DELETE:
                call = service.delete(URL, PAPARMS);
                break;
            case UOLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), file);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                call = RestCreator.geRestSevice().upload(URL, body);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RestCallBack(IREQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE, id);
    }

    public final void get() {
        request(HttpMethod.GET);
    }
    public  final void postByHeaders(){
        request(HttpMethod.POST_HEADERS);
    }

    public final void post() {
        if (BOBY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PAPARMS.isEmpty()) {
                throw new RuntimeException("params must be null..");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {

        if (BOBY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PAPARMS.isEmpty()) {
                throw new RuntimeException("params must be null..");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void download() {
        new DownloadHandler(URL, IREQUEST, DOWNLOAD_DIR, EXTENSION, Name, SUCCESS, FAILURE, ERROR, id,CONTEXT).handleDownload();
    }

    public final void cancel() {
        if (call != null) {
            if (!call.isCanceled()){
                call.cancel();
            }
        }
    }
}
