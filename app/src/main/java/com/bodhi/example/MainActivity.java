package com.bodhi.example;

import android.app.AlarmManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bodhi.http.HttpCore;
import com.bodhi.http.HttpRequestListener;
import com.bodhi.http.exception.URLNullException;
import com.bodhi.http_core.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化HttpCore
        HttpCore.getInstance().init(this);


        AccountManager.getInstance().requestMemberInfo();
//        testRequest();

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
}
