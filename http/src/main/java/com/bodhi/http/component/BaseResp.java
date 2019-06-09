package com.bodhi.http.component;

/**
 * @author : Sun
 * @version : 1.0
 * create time : 2018/11/12 15:38
 * desc :
 */
public class BaseResp {
    private String message;
    private int status;
    private String result;
    private String msg;
    private int code;


    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result == null ? "" : result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", code=" + code +
                '}';
    }
}
