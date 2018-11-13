package com.bodhi.http;

/**
 * @author : Sun
 * @version : 1.0
 * create time : 2018/11/12 16:15
 * desc :
 */
public interface HttpRequestListener<T> {
    void onResult(T t);

    void onError(int errorCode, String errorMsg);
}
