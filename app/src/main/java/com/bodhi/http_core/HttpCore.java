package com.bodhi.http_core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;

import com.bodhi.http_core.component.BaseResp;
import com.bodhi.http_core.component.ParamMap;
import com.bodhi.http_core.component.SSLParams;
import com.bodhi.http_core.exception.URLNullException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * @author : Sun
 * @version : 1.0
 * create time : 2018/11/12 16:17
 * desc :
 */
public class HttpCore implements Defines {
    final public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    final public static MediaType MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    private static HttpCore httpCore;

    public static HttpCore getInstance() {
        if (httpCore != null) {
            return httpCore;
        }

        httpCore = new HttpCore();
        return httpCore;
    }

    private final String TAG = HttpCore.class.getSimpleName();
    private Context context;
    private OkHttpClient httpClient = null;

    private String ua;

    public void init(Context context) {
        init(context, null);
    }

    public void init(Context context, SSLParams sslParams) {
        this.context = context;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10000L, TimeUnit.MILLISECONDS);
        builder.readTimeout(10000L, TimeUnit.MILLISECONDS);

        if (sslParams != null) {
            builder.sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager);
        }

        httpClient = builder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                ua = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                ua = System.getProperty("http.agent");
            }
        } else {
            ua = System.getProperty("http.agent");
        }
    }

    public void get(final String url) throws URLNullException {
        get(url, null, null, null);
    }

    public void get(final String url, final ParamMap urlParamMap) throws URLNullException {
        get(url, urlParamMap, null, null);
    }

    public <T extends BaseResp> void get(final String url, final Class<T> resultClz, final HttpRequestListener<T> callback) throws URLNullException {
        get(url, null, resultClz, callback);
    }

    public <T extends BaseResp> void get(final String url, final ParamMap urlParamMap, final Class<T> resultClz, final HttpRequestListener<T> callback) throws URLNullException {
        final String requestUrl = getFinalUrl(url, urlParamMap);
        Request.Builder builder = new Request.Builder().url(requestUrl).addHeader("User-Agent", ua);
        request(builder.build(), resultClz, callback);
    }

    public <T extends BaseResp> void post(final String url, final RequestBody body,final Class<T> resultClz,final HttpRequestListener<T> callback) throws URLNullException {
        post(url,null,body,resultClz,callback);
    }

    public <T extends BaseResp> void post(final String url,final ParamMap urlParamMap,final Class<T> resultClz,final HttpRequestListener<T> callback) throws URLNullException {
        post(url,urlParamMap,null,resultClz,callback);
    }

    public <T extends BaseResp> void post(final String url,final ParamMap urlParamMap,final RequestBody body ) throws URLNullException {
        post(url,urlParamMap,body,null,null);
    }

    public <T extends BaseResp> void post(final String url,final ParamMap urlParamMap,final RequestBody body,final Class<T> resultClz,final HttpRequestListener<T> callback) throws URLNullException {
        String requestUrl = getFinalUrl(url, urlParamMap);
        Request.Builder builder = new Request.Builder().url(requestUrl).addHeader("User-Agent", ua);

        if(body!=null){
            builder.post(body);
        }else{
            builder.post(Util.EMPTY_REQUEST);
        }

        request(builder.build(),resultClz,callback);
    }


    private <T extends BaseResp> void request(Request request, final Class<T> resultClz, final HttpRequestListener<T> callback) {
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onError(CODE_NET_ERROR, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    if (response.isSuccessful() && response.code() == 200) {
                        String result = response.body().toString();
                        if (TextUtils.isEmpty(result)) {
                            callback.onError(CODE_RETURN_NULL, "server return null");
                        } else {
                            try {
                                T t = new Gson().fromJson(result, resultClz);
                                if (t.getStatus() == CODE_SUCCESS) {
                                    callback.onResult(t);
                                } else {
                                    callback.onError(t.getCode(), t.getMessage());
                                }
                            } catch (JsonSyntaxException jse) {
                                callback.onError(CODE_PARSE_ERROR, "result string is " + result);
                            }
                        }
                    } else {
                        callback.onError(CODE_NET_ERROR, response.message());
                    }
                }
            }
        });
    }

    public void bitmapGet(final String url,final ParamMap urlParamMap,final HttpRequestListener<Bitmap> callback) throws URLNullException {
        String requestUrl = getFinalUrl(url, urlParamMap);
        Request.Builder builder = new Request.Builder().url(requestUrl).addHeader("User-Agent", ua);

        httpClient.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback!=null) {
                    callback.onError(CODE_NET_ERROR,e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback!=null) {
                    if(response.isSuccessful()&&response.code()==200){
                        Bitmap result = BitmapFactory.decodeStream(response.body().byteStream());
                        if (result!=null) {
                            callback.onResult(result);
                        }else{
                            callback.onError(CODE_PARSE_ERROR,"图片解析错误");
                        }
                    }else{
                        callback.onError(CODE_NET_ERROR,response.message());
                    }
                }
            }
        });
    }

    public <T> void anyGet(final String url,final ParamMap urlParamMap,final Class<T> resultClz,final HttpRequestListener<T> callback) throws URLNullException {
        String requestUrl = getFinalUrl(url, urlParamMap);

        Request.Builder builder = new Request.Builder().url(requestUrl).addHeader("User-Agent", ua);
        anyRequest(builder.build(),resultClz,callback);
    }

    public <T> void anyPost(final String url,final ParamMap urlParamMap,final RequestBody body,final Class<T> resultClz,final HttpRequestListener<T> callback) throws URLNullException {
        String requestUrl = getFinalUrl(url, urlParamMap);
        Request.Builder builder = new Request.Builder().url(requestUrl).addHeader("User-Agent", ua);

        if(body!=null){
            builder.post(body);
        }else{
            builder.post(Util.EMPTY_REQUEST);
        }

        anyRequest(builder.build(),resultClz,callback);
    }

    private <T> void anyRequest(Request request, final Class<T> resultClz, final HttpRequestListener<T> callback) {
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(callback!=null){
                    callback.onError(CODE_NET_ERROR,e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(callback!=null){
                    if(response.isSuccessful()&&response.code()==200){
                        String result = response.body().string();

                        try {
                            callback.onResult(new Gson().fromJson(result,resultClz));
                        }catch (JsonSyntaxException jse){
                            callback.onError(CODE_PARSE_ERROR, "result string is " + result);
                        }
                    }else{
                        callback.onError(CODE_NET_ERROR,response.message());
                    }
                }
            }
        });
    }

    public void simpleGet(final String url,final ParamMap urlParamMap,final  HttpRequestListener<String> callback) throws URLNullException {
        String requestUrl = getFinalUrl(url, urlParamMap);
        Request.Builder builder = new Request.Builder().url(requestUrl).addHeader("User-Agent", ua);
        simpleRequest(builder.build(),callback);

    }

    public void simplePost(final String url,final ParamMap urlParamMap,final RequestBody body,final HttpRequestListener<String> callback) throws URLNullException {
        String requestUrl = getFinalUrl(url, urlParamMap);
        Request.Builder builder = new Request.Builder().url(requestUrl).addHeader("User-Agent", ua);

        if(body!=null){
            builder.post(body);
        }else{
            builder.post(Util.EMPTY_REQUEST);
        }

        simpleRequest(builder.build(),callback);
    }

    private void simpleRequest(Request request, final HttpRequestListener<String> callback) {
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(callback!=null){
                    callback.onError(CODE_NET_ERROR,e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(callback!=null){
                    if(response.isSuccessful()&&response.code()==200){
                        callback.onResult(response.body().string());
                    }else{
                        callback.onError(CODE_NET_ERROR,response.message());
                    }
                }
            }
        });
    }

    public String getUA(){
        return ua;
    }

    private String getFinalUrl(String url, ParamMap urlParamMap) throws URLNullException {
        if (TextUtils.isEmpty(url)) {
            throw new URLNullException();
        }

        StringBuffer requestUrl = new StringBuffer(url);
        if (urlParamMap != null) {
            String urlParamString = urlParamMap.getUrlParamString();
            if (!TextUtils.isEmpty(urlParamString)) {
                requestUrl.append("?").append(urlParamMap);
            }
        }

        return requestUrl.toString();
    }

}
