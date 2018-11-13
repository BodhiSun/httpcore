package com.bodhi.http_core.exception;

/**
 * @author : Sun
 * @version : 1.0
 * create time : 2018/11/12 15:47
 * desc :
 */
public class URLNullException extends Exception {
    public URLNullException() {
        super("request url is null");
    }
}
