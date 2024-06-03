package com.example.app1;

import android.app.Application;

public class MovieTAG extends Application {
    // 使用静态变量来存储当前点击的电影类型
    private static String MovieTAG;

    @Override
    public void onCreate() {
        super.onCreate();
        // 可以在此处进行初始化操作
    }

    public static void setCurrentTAG(String movieTAG) {
        MovieTAG = movieTAG;
    }

    public static String getCurrentTAG() {
        return MovieTAG;
    }
}