package com.bodhi.http.component;

import android.text.TextUtils;

import com.bodhi.http.exception.DuplicateParamException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author : Sun
 * @version : 1.0
 * create time : 2018/11/12 15:42
 * desc :
 */
public class ParamMap {
    public static ParamMap create() {
        return new ParamMap();
    }

    private Map<String, String> paramMap = new HashMap<>();

    public ParamMap param(String key, String value) throws DuplicateParamException {
        if (paramMap.containsKey(key)) {
            throw new DuplicateParamException(key);
        }

        String finalValue = null;
        try {
            finalValue = TextUtils.isEmpty(value) ? "" : URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        paramMap.put(key, finalValue);
        return this;
    }

    public String getUrlParamString() {
        if (paramMap.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }

            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return sb.toString();
    }


    public String getSortedUrlParamString() {
        if (paramMap.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        List<String> keys = new ArrayList<>();
        keys.addAll(paramMap.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (String key : keys) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }
            sb.append(key).append("=").append(paramMap.get(key));
        }

        return sb.toString();
    }

    public void append(ParamMap source) {
        if (source == null) {
            return;
        }

        if (source.paramMap.isEmpty()) {
            return;
        }

        paramMap.putAll(source.paramMap);
    }

    public void update(String key, String value) {
        if (paramMap.containsKey(key)) {
            paramMap.put(key, value);
        }
    }

    public String getValue(String key) {
        return paramMap.get(key);
    }

    public static ParamMap cloneFrom(ParamMap mp) {
        ParamMap result = new ParamMap();
        result.paramMap.putAll(mp.paramMap);
        return result;
    }
}
