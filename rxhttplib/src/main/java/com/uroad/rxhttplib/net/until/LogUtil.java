package com.uroad.rxhttplib.net.until;

import android.util.Log;

/**
 * 日志工具，打日志统一调用该类方法。
 * 当GlobalData.isDebug为false时，不打印日志
 * Created by hrd on 2017/8/16 0016.
 */

public class LogUtil {

    /**
     * 控制是否输出日志,默认不输出日志
     * 配置方法：
     * 在CurrApplication里配置 LogUtil.isPrintLog = GlobalData.isDebug;
     * */
    public static boolean isPrintLog = true;
    public static void i(String TAG,String log){
        if(isPrintLog){
            Log.i(TAG,log);
        }
    }

    public static void d(String TAG,String log){
        if(isPrintLog){
            Log.d(TAG,log);
        }
    }

    public static void e(String TAG,String log){
        if(isPrintLog){
            Log.e(TAG,log);
        }
    }

    public static void w(String TAG,String log){
        if(isPrintLog){
            Log.w(TAG,log);
        }
    }
    public static void isDebug( boolean is){
        isPrintLog = is;
    }
}
