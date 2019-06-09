package com.bodhi.example;

import android.app.AlarmManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bodhi.example.screenshot.ScreenShotActivity;
import com.bodhi.http.HttpCore;
import com.bodhi.http.HttpRequestListener;
import com.bodhi.http.component.ParamMap;
import com.bodhi.http.exception.DuplicateParamException;
import com.bodhi.http.exception.URLNullException;
import com.bodhi.http_core.R;

import java.io.File;

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

//        testRequest2();

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testUpload();
            }
        });

    }

    private void testUpload() {
        startActivityForResult(new Intent(this, ScreenShotActivity.class),101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101&&resultCode==102){
            String imagePath = data.getStringExtra("imagePath");
            if(!TextUtils.isEmpty(imagePath)){
                Log.e("upload","---imagePath:"+imagePath);

                String url="http://122.228.113.232:9999/service/default/upload";
                String testUrl="http://60.205.180.159:8888/service/default/upload";
                String mediaType="image/*";

                HttpCore.getInstance().upLoadFile(url,null,null, imagePath, mediaType, new HttpRequestListener<String>() {
                    @Override
                    public void onResult(String s) {
                        Log.e("upload","---onResult s:"+s);

                    }

                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        Log.e("upload","---onError  errorCode:"+errorCode+" errorMsg:"+errorMsg);

                    }
                });
            }
        }
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

            HttpCore.getInstance().simpleGet("https://api.zhetaoke.com:10001/api/api_all.ashx",null, paramMap, new HttpRequestListener<String>() {
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
                    HttpCore.getInstance().simpleGet(url, null,null, new HttpRequestListener<String>() {
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
                    HttpCore.getInstance().simpleGet(url, null,null, new HttpRequestListener<String>() {
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
