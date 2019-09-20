package com.uroad.rxhttplib.net.views;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
  *
  * @Description: 加载框
  * @Author:         chufeng
  * @CreateDate:    2019/4/22 22:08
 */
public class LoaderCreator {
    private static final WeakHashMap<String, Indicator> LOADING_MAP=new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type)==null){
          final Indicator indicator =getIndicator(type);
          LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }


    private  static  Indicator getIndicator(String name){
        if (name==null||name.isEmpty()){
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".") ) {
            final String defultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defultPackageName).append(".indicators").append(".");
        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
