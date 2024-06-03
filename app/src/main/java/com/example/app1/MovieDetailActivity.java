package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_RES_ID = "extra_image_res_id";
    private SeatDBHelper seatDBHelper;
    private DatabaseHelper databaseHelper;
    private RecyclerView seatRecyclerView;
    private SeatAdapter seatAdapter;
    private List<Seat> seatList;
    private int numberOfColumns = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        // 获取传递过来的图片资源ID
        int imageResId = getIntent().getIntExtra(EXTRA_IMAGE_RES_ID, 0);
        String MovieID = Integer.toString(imageResId);
        if (imageResId != 0) {
            // 使用图片资源ID来设置图片
            ImageView imageView = findViewById(R.id.movie_image);
            imageView.setImageResource(imageResId);
        }


        //热映电影处理
        if (MovieTAG.getCurrentTAG().equals("hot")){
            //座位预订部分
            seatRecyclerView = findViewById(R.id.seat_recycler_view);
            String username = User.getCurrentUsername();
            seatDBHelper = new SeatDBHelper(this);
            seatList = (List<Seat>) seatDBHelper.getSeatByMovie(MovieID);// 从数据库获取座位数据
            seatAdapter = new SeatAdapter(this, seatList);
            seatRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
            seatRecyclerView.setAdapter(seatAdapter);

            seatAdapter.setOnSeatClickListener(seat -> {
                if (!seat.isBooked() && !username.isEmpty()) {
                    // 更新座位状态为已预订
                    seat.setBooked(true);
                    // 更新数据库...
                    seatDBHelper.updateSeatBooking(seat.getSeatNumber(),seat.isBooked(),seat.getMovieID(),username);
                    // 更新UI
                    seatAdapter.notifyItemChanged(getSeatPosition(seat));
                    Toast.makeText(this, "订票成功", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(this, "请检查用户名输入或您所选座位已被预订", Toast.LENGTH_SHORT).show();
            });
        }

        //即将上映电影处理
        else if (MovieTAG.getCurrentTAG().equals("future")) {

        }

    }
    private int getSeatPosition(Seat seat) {
        // 遍历座位列表以找到 Seat 对象的索引位置
        for (int i = 0; i < seatList.size(); i++) {
            Seat currentSeat = seatList.get(i);
            if (currentSeat.getSeatID() == seat.getSeatID()) {
                // 如果找到匹配的 Seat 对象，返回其索引位置
                return i;
            }
        }
        // 如果没有找到 Seat 对象，返回 -1 或其他表示未找到的值
        return -1;
    }
    public void GoFirst(View btn){
        Intent intent = new Intent(MovieDetailActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
