package com.example.app1;

public class Movie {
    private String title;
    private String imageUrl;
    // 可以添加更多属性，如描述、评分等

    // 构造函数、getter 和 setter 方法
    public Movie(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}