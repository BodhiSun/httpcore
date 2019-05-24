package com.bodhi.example;

import android.app.AlarmManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bodhi.http.HttpCore;
import com.bodhi.http.HttpRequestListener;
import com.bodhi.http.component.ParamMap;
import com.bodhi.http.exception.DuplicateParamException;
import com.bodhi.http.exception.URLNullException;
import com.bodhi.http_core.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化HttpCore
        HttpCore.getInstance().init(this,false);


//        AccountManager.getInstance().requestMemberInfo();
//        testRequest();

//        testRequest1();

        testRequest2();

    }

    private void testRequest2() {
        ParamMap paramMap = new ParamMap();
        try {
            paramMap.param("page","1");
            paramMap.param("appkey","a0c6070d3b7d47bdb645c9342b652ab2");
            paramMap.param("page_size","20");
            paramMap.param("sort","new");
            paramMap.param("cid","1");
            paramMap.param("q","");

            HttpCore.getInstance().simpleGet("https://api.zhetaoke.com:10001/api/api_all.ashx", paramMap, new HttpRequestListener<String>() {
                @Override
                public void onResult(String s) {
                    Log.e("aaa", "onResult   s:" + s);
                }

                @Override
                public void onError(int errorCode, String errorMsg) {
                    Log.e("aaa", "---errorCode:" + errorCode+"---errorMsg:"+errorMsg);

                }
            });
        } catch (DuplicateParamException e) {
            e.printStackTrace();
        } catch (URLNullException e) {
            e.printStackTrace();
        }
    }

    private void testRequest() {
        ThreadManager.getInstance().post(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://localhost:8080/apiTest";
                    HttpCore.getInstance().simpleGet(url, null, new HttpRequestListener<String>() {
                        @Override
                        public void onResult(String s) {
                            Log.e("aaa", "onResult   s:" + s);
                        }

                        @Override
                        public void onError(int errorCode, String errorMsg) {
                            Log.e("aaa", "onError   errorCode:" + errorCode+"   errorMsg:"+errorMsg);

                        }
                    });
                } catch (URLNullException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void testRequest1() {
        ThreadManager.getInstance().post(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "https://api.zhetaoke.com:10001/api/api_all.ashx?appkey=a0c6070d3b7d47bdb645c9342b652ab2&page=1&page_size=20&sort=new&cid=1&q=";
                    HttpCore.getInstance().simpleGet(url, null, new HttpRequestListener<String>() {
                        @Override
                        public void onResult(String s) {
                            Log.e("aaa", "onResult   s:" + s);
                        }

                        @Override
                        public void onError(int errorCode, String errorMsg) {
                            Log.e("aaa", "---errorCode:" + errorCode+"---errorMsg:"+errorMsg);

                        }
                    });
                } catch (URLNullException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
