package com.bodhi.example.screenshot;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by wei on 16-9-18.
 * <p>
 * 完全透明 只是用于弹出权限申请的窗而已
 *
 * 这个类的用于service需要在manifest中配置 intent-filter action
 */
public class ScreenShotActivity extends Activity {

    public static final int REQUEST_MEDIA_PROJECTION = 0x2893;
    public static final int REQUEST_SCREEN_SHOT_RESULT = 0x2894;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setTheme(android.R.style.Theme_Dialog);//这个在这里设置 之后导致 的问题是 背景很黑
        super.onCreate(savedInstanceState);

        //如下代码 只是想 启动一个透明的Activity 而上一个activity又不被pause
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setDimAmount(0f);

        requestScreenShot();
    }

    public void requestScreenShot() {
        if (Build.VERSION.SDK_INT >= 21) {
            startActivityForResult(createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        } else {
            toast("版本过低,无法截屏");
        }
    }

    private Intent createScreenCaptureIntent() {
        //这里用media_projection代替Context.MEDIA_PROJECTION_SERVICE 是防止低于21 api编译不过
        return ((MediaProjectionManager) getSystemService("media_projection")).createScreenCaptureIntent();
    }

    private void toast(String str) {
        Toast.makeText(ScreenShotActivity.this, str, Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION: {
                if (resultCode == RESULT_OK && data != null) {
                    Shotter shotter = new Shotter(ScreenShotActivity.this, resultCode, data);
                    shotter.startScreenShot(new Shotter.OnShotListener() {
                        @Override
                        public void onFinish(String imagePath) {
                            toast("shot finish!");
                            Log.e("zzz","   Shotter path:"+imagePath );

                            setResult(102,getIntent().putExtra("imagePath",imagePath));
                            finish(); // don't forget finish activity
                        }
                    });
                } else if (resultCode == RESULT_CANCELED) {
                    toast("shot cancel , please give permission.");
                } else {
                    toast("unknow exceptions!");
                }
            }
        }
    }


}