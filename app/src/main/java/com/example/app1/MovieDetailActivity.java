package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_RES_ID = "extra_image_res_id";
    private SeatDBHelper seatDBHelper;
    private RecyclerView seatRecyclerView;
    private SeatAdapter seatAdapter;
    private List<Seat> seatList;
    private TextView fname,fdata,fstarring,fscore;
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
            //电影信息
        fname = findViewById(R.id.text_item_film_name);
        fdata = findViewById(R.id.text_item_released_time);
        fstarring = findViewById(R.id.text_item_starring);
        fscore = findViewById(R.id.text_item_score);
        if(imageResId ==2131165322){
            fname.setText("哆啦A梦：大雄的地球交响乐");
            fdata.setText("2024.05.31");
            fstarring.setText("主演：水田山葵，大原惠美");
            fscore.setText("评分：9.3");
        }
        else if(imageResId ==2131165323){
            fname.setText("加菲猫家族");
            fdata.setText("2024.06.01");
            fstarring.setText("主演：Chris Pratt,Samuel L.Jackson");
            fscore.setText("评分9.2");
        }
        else if(imageResId ==2131165324){
            fname.setText("三叉戟");
            fdata.setText("2024.05.24");
            fstarring.setText("主演：姜武，郭涛，黄志忠");
            fscore.setText("暂无评分");
        }

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
