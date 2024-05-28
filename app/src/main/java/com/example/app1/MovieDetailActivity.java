package com.example.app1;

import static com.example.app1.SeatDBHelper.COLUMN_BOOKED_BY;
import static com.example.app1.SeatDBHelper.COLUMN_IS_BOOKED;
import static com.example.app1.SeatDBHelper.COLUMN_MOVIE_ID;
import static com.example.app1.SeatDBHelper.COLUMN_SEAT_ID;
import static com.example.app1.SeatDBHelper.COLUMN_SEAT_NUMBER;
import static com.example.app1.SeatDBHelper.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_RES_ID = "extra_image_res_id";
    private SeatDBHelper seatDBHelper;
    private DatabaseHelper databaseHelper;
    private EditText Username;

    private List<Seat> seats;

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
        //座位预订部分
        seatDBHelper = new SeatDBHelper(this);
        seats = (List<Seat>) seatDBHelper.getSeat(MovieID); // 从数据库获取座位数据
        for (Seat seat : seats) {
            String seatNumber = seat.getSeatNumber();
            boolean isbooked = seat.isBooked();
            String bookedBy = seat.getBookedBy();
            String imageid ="R.id."+seatNumber;
            ImageView seatView= findViewById(Integer.parseInt(imageid));
            // 设置座位图片和点击事件
            seatView.setImageResource(isbooked ? R.drawable.seat_booked : R.drawable.seat_available);
            if(!isbooked){
                seatView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Username = findViewById(R.id.username_buy);
                        String username = Username.getText().toString().trim();
                        // 更新座位预订状态并保存到数据库
                        Seat seat = findSeatByView(v,MovieID);
                        String seatNumber = seat.getSeatNumber();
                        boolean isbooked = seat.isBooked();

                        boolean newStatus = !seat.isBooked();
                        seat.setBooked(newStatus);
                        seatDBHelper.updateSeatBooking(seatNumber,isbooked,MovieID,username);
                        // 更新座位视图
                        ((ImageView) v).setImageResource(newStatus ? R.drawable.seat_booked : R.drawable.seat_available);
                    }
                });
            }

        }
    }
    private Seat findSeatByView(View view,String MovieID) {
        // 从视图的 tag 中获取座位号
        String seatNumber = (String) view.getTag();
        if (seatNumber != null) {
            // 使用座位号和电影ID在数据库中查找座位记录
            SQLiteDatabase db = seatDBHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_NAME +
                            " WHERE " + COLUMN_SEAT_NUMBER + " = ? AND " +
                            COLUMN_MOVIE_ID + " = ?",
                    new String[] {seatNumber, MovieID}
            );

            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") Seat seat = new Seat(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_SEAT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SEAT_NUMBER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_IS_BOOKED)) == 1,
                        cursor.getString(cursor.getColumnIndex(COLUMN_BOOKED_BY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID))
                );
                cursor.close();
                return seat;
            }
        }
        return null;
    }
    public void GoFirst(View btn){
        Intent intent = new Intent(MovieDetailActivity.this,PwdFindActivity.class);
        startActivity(intent);
    }
}
