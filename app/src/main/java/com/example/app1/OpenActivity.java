package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class OpenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_open);

            // 使用Handler延迟五秒执行跳转操作
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 五秒后跳转到MainActivity
                    Intent intent = new Intent(OpenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // 结束OpenActivity
                }
            }, 3000); // 5000毫秒即5秒
        }
    }