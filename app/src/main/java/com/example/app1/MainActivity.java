package com.example.app1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerViewTop, recyclerViewBottom;
    private MainImageAdapter mainImageAdapterTop, mainImageAdapterBottom;
    int[] HotimageIds = {
            R.drawable.hot1, R.drawable.hot2, R.drawable.hot3,
            // 更多图片资源ID...
    };
    int[] FutureimageIds = {
            R.drawable.future1, R.drawable.future2, R.drawable.future3,
            // 更多图片资源ID...
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 RecyclerView
        recyclerViewTop = findViewById(R.id.rv_hot);
        recyclerViewBottom = findViewById(R.id.rv_future);
        // 准备图片资源数组
        int[] imageResIdsTop = {R.drawable.hot1, R.drawable.hot2, R.drawable.hot3,};
        int[] imageResIdsBottom = {R.drawable.future1, R.drawable.future2, R.drawable.future3,};

        // 创建适配器并设置给 RecyclerView
        mainImageAdapterTop = new MainImageAdapter(this, imageResIdsTop);
        mainImageAdapterBottom = new MainImageAdapter(this, imageResIdsBottom);

        recyclerViewTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        recyclerViewTop.setAdapter(mainImageAdapterTop);
        recyclerViewBottom.setAdapter(mainImageAdapterBottom);

        mainImageAdapterTop.setOnImageClickListener(new MainImageAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                // 处理图片点击事件，例如跳转到详情页面
                showImageDetail(imageResIdsTop[position]);
            }
        });

        mainImageAdapterBottom.setOnImageClickListener(new MainImageAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                // 处理图片点击事件
                showImageDetail(imageResIdsBottom[position]);
            }
        });
    }
    private void showImageDetail(int imageResId) {
        // 根据图片资源ID跳转到对应的界面
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_IMAGE_RES_ID, imageResId);
        startActivity(intent);
    }

    }

