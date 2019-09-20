package com.uroad.rxhttplib.net.views;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;
import com.blankj.utilcode.util.ScreenUtils;

import com.uroad.rxhttplib.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class LatteLoader {
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoaderStytle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStytle> type) {
        showLoading(context, type.name());

    }

    public static void showLoading(Context context, String type) {
        Context contextWeakReference = new WeakReference<>(context).get();
        final AppCompatDialog dialog = new AppCompatDialog(contextWeakReference, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, contextWeakReference);
        avLoadingIndicatorView.setIndicatorColor(Color.parseColor("#FF4690FF"));
        dialog.setContentView(avLoadingIndicatorView);

        int screenWidth = ScreenUtils.getScreenWidth();
        int screenHeight = ScreenUtils.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = screenWidth / LOADER_SIZE_SCALE;
            lp.height = screenHeight / LOADER_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
        LOADERS.clear();

    }

}
