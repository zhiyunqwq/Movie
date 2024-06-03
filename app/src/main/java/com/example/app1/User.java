package com.example.app1;

import android.app.Application;

public class User extends Application {
    // 使用静态变量来存储当前登录的用户名
    private static String currentUsername;

    @Override
    public void onCreate() {
        super.onCreate();
        // 可以在此处进行初始化操作
    }

    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }
}