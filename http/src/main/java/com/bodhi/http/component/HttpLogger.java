package com.bodhi.http.component;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author : Sun
 * @version : 1.0
 * create time : 2019/5/24 17:47
 * desc :
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.e("OkHttpLog", message);
    }
}