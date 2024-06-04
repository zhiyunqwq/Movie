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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FutureMovieActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_RES_ID = "extra_image_res_id";
    private SeatDBHelper seatDBHelper;
    private String MovieID;
    private RecyclerView seatRecyclerView;
    private TextView Numberbooked,fname,fdata,fstarring,fscore;
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
            fname = findViewById(R.id.text_item_film_name);
            fdata = findViewById(R.id.text_item_released_time);
            fstarring = findViewById(R.id.text_item_starring);
            fscore = findViewById(R.id.text_item_score);
            seatRecyclerView = findViewById(R.id.poster_view);
            String username = User.getCurrentUsername();
            int[] imageResIdsFuture1 = {R.drawable.f1poster1, R.drawable.f1poster2, R.drawable.f1poster3,R.drawable.f1poster4,R.drawable.f1poster5};
            int[] imageResIdsFuture2 = {R.drawable.f2poster1, R.drawable.f2poster2, R.drawable.f2poster3,R.drawable.f2poster4,R.drawable.f2poster5};
            int[] imageResIdsFuture3 = {R.drawable.f3poster1, R.drawable.f3poster2, R.drawable.f3poster3,R.drawable.f3poster4,R.drawable.f3poster5};
            if(imageResId ==2131165319){
                mainImageAdapterFuture = new MainImageAdapter(this, imageResIdsFuture1);
                fname.setText("美国内战");
                fdata.setText("2024.06.07");
                fstarring.setText("主演：Kirsten Dunst,Wagner Moura");
                fscore.setText("期待值：9.0");
            }
            else if(imageResId ==2131165320){
                mainImageAdapterFuture = new MainImageAdapter(this, imageResIdsFuture2);
                fname.setText("九龙城寨");
                fdata.setText("2024.08.01");
                fstarring.setText("主演：古天乐，洪金宝，任贤齐");
                fscore.setText("期待值：9.9");
            }
            else if(imageResId ==2131165321){
                mainImageAdapterFuture = new MainImageAdapter(this, imageResIdsFuture3);
                fname.setText("来福大酒店");
                fdata.setText("2024.06.28");
                fstarring.setText("主演：黄轩，柳岩");
                fscore.setText("期待值：9.5");
            }
            seatRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            seatRecyclerView.setAdapter(mainImageAdapterFuture);

           //显示当前有多少用户预约了此电影
            Numberbooked = findViewById(R.id.text_number);
            SQLiteDatabase db = seatDBHelper.getReadableDatabase();
            String selection = COLUMN_MOVIE_ID + "=?";
            String[] columns = {COLUMN_SEAT_ID};
            String[] selectionArgs = {MovieID};

             Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();
            Numberbooked.setText("当前已有"+count+"名用户也预约了此电影");


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
                Toast.makeText(this, "预约成功，请自行返回首页", Toast.LENGTH_SHORT).show();
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