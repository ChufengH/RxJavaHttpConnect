package com.uroad.rxhttplib.net.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;


import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.uroad.rxhttplib.net.config.Config;
import com.uroad.rxhttplib.net.impl.IRequest;
import com.uroad.rxhttplib.net.impl.ISuccess;
import com.uroad.rxhttplib.net.until.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object, Void, File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final String id;
    private  Context context;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS, String id, Context context) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.id =id;
        this.context = context;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDor = (String) params[0];   //文件夹
        String extension = (String) params[1];  //后缀名
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDor == null || downloadDor.equals("")) {
            downloadDor = AppUtils.getAppPackageName()+ "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null || name.equals("")) {
            return FileUtil.writeToDisk(is, downloadDor, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDor, name);
        }

    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath(),id);
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtils.getFileExtension(file.getPath()).equals("apk")) {
            final Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//            Config.application.getContext().startActivity(intent);
            context.getApplicationContext().startActivity(intent);
        }
    }
}
