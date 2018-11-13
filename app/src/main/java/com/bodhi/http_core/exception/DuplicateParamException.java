package com.bodhi.http_core.exception;

/**
 * @author : Sun
 * @version : 1.0
 * create time : 2018/11/12 15:45
 * desc :
 */
public class DuplicateParamException extends Exception {

    public DuplicateParamException(String key) {
        super("duplicate param with key ( " + key + " )");
    }
}
