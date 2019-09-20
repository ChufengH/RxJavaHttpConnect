package com.uroad.rxhttplib.net.config;

import android.app.Application;
import com.uroad.rxhttplib.net.until.LogUtil;

public class RxHttpConnect {
    public static final String base_url = "https://www.baidu.com/";
    public static Application application = null;
    public static String key = "";
    public static String code = "";

    public static void init(String keys, String s, Boolean isDebug) {
        key = keys;
        code = s;
        LogUtil.isPrintLog = isDebug;
    }
}
