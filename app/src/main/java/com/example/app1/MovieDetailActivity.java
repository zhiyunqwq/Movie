package com.example.app1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // 通过 Intent 获取电影数据
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {
            // 使用电影数据填充 UI
            ImageView movieDetailImage = findViewById(R.id.movie_image);
            // 假设我们有一个方法来加载图片
            loadMovieImage(movieDetailImage, movie.getImageUrl());
            // 设置其他电影详情...
        }
    }

    // 用于加载电影图片的方法
    private void loadMovieImage(ImageView imageView, String imageUrl) {
       Glide.with(this).load(imageUrl).into(imageView);
    }

    // 供外部调用，启动 MovieDetailActivity 的方法
    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}