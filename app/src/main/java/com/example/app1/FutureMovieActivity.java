package com.example.app1;

import static com.example.app1.SeatDBHelper.COLUMN_BOOKED_BY;
import static com.example.app1.SeatDBHelper.COLUMN_MOVIE_ID;
import static com.example.app1.SeatDBHelper.COLUMN_SEAT_ID;
import static com.example.app1.SeatDBHelper.TABLE_NAME;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FutureMovieActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_RES_ID = "extra_image_res_id";
    private SeatDBHelper seatDBHelper;
    private String MovieID;
    private RecyclerView seatRecyclerView;
    private MainImageAdapter mainImageAdapterFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seatDBHelper = new SeatDBHelper(this);
        setContentView(R.layout.activity_future_movie);

        // 获取传递过来的图片资源ID
        int imageResId = getIntent().getIntExtra(EXTRA_IMAGE_RES_ID, 0);
        MovieID = Integer.toString(imageResId);
        if (imageResId != 0) {
            // 使用图片资源ID来设置图片
            ImageView imageView = findViewById(R.id.movie_image);
            imageView.setImageResource(imageResId);
        }
        //即将上映电影处理
            seatRecyclerView = findViewById(R.id.poster_view);
            String username = User.getCurrentUsername();
            int[] imageResIdsFuture = {R.drawable.poster1, R.drawable.poster2, R.drawable.poster3,R.drawable.poster4};
            mainImageAdapterFuture = new MainImageAdapter(this, imageResIdsFuture);
            seatRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            seatRecyclerView.setAdapter(mainImageAdapterFuture);

    }
    public void Confirm(View btn) {
        SQLiteDatabase db = seatDBHelper.getReadableDatabase();
        try {
            String selection = COLUMN_MOVIE_ID + "=?"+ " AND " + COLUMN_BOOKED_BY + "=?";
            String[] columns = {COLUMN_SEAT_ID};
            String[] selectionArgs = {MovieID,User.getCurrentUsername()};

            Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.getCount() == 0) {
                seatDBHelper.addSeat("future", User.getCurrentUsername(), MovieID);
                Toast.makeText(this, "预约成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "您已预约过此电影，即将自动返回主页", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(FutureMovieActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // 结束当前活动
                    }
                }, 1000); // 5秒后跳转
            }
        } finally {
            if (db != null && db.isOpen()) {
                db.close(); // 关闭数据库
            }
        }
    }
    public void GoFirst(View btn){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}