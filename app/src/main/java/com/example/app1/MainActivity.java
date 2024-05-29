package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends Activity {
    private SeatDBHelper seatDBHelper;
    private RecyclerView recyclerViewTop, recyclerViewBottom;
    private MainImageAdapter mainImageAdapterTop, mainImageAdapterBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seatDBHelper = new SeatDBHelper(this);
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
        /*
        //在点击跳转时，为电影创建座位数据
        SQLiteDatabase db = this.seatDBHelper.getReadableDatabase();
        String selection = COLUMN_SEAT_NUMBER + "=?" + " AND " + COLUMN_MOVIE_ID + "=?";
        String[] columns = {
                COLUMN_SEAT_ID
        };
        for (int i = 1; i <= 5; i++) {
            String seatNumber = "seat" + i;
            String[] selectionArgs = {seatNumber, "" + imageResId};

            Cursor cursor = db.query(TABLE_NAME,columns, selection, selectionArgs, null, null, null);
            int cursorCount = cursor.getCount();
            if (cursorCount == 0){
                seatDBHelper.addSeat(seatNumber, null, "" + imageResId);
            }

        }*/

        // 根据图片资源ID跳转到对应的界面
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_IMAGE_RES_ID, imageResId);
        startActivity(intent);
    }

    }

