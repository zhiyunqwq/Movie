package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MineActivity extends AppCompatActivity {
    String currentUsername = User.getCurrentUsername();
    private RecyclerView recyclerView_Orders;
    private OrderAdapter orderAdapter;
    private List<Order> OrderList = new ArrayList<>();
    private SeatDBHelper seatDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seatDBHelper = new SeatDBHelper(this);
        setContentView(R.layout.activity_mine);
        List<Seat> seatByUser = seatDBHelper.getSeatByUser(currentUsername);

        if (seatByUser != null) {
            for (int i = 0; i < seatByUser.size(); i++){
                Seat seat = seatByUser.get(i);
                Order order = transSeat(seat);
                OrderList.add(order);
            }
            recyclerView_Orders = findViewById(R.id.recyclerView_Orders);
            recyclerView_Orders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            orderAdapter = new OrderAdapter(this,OrderList);
            recyclerView_Orders.setAdapter(orderAdapter);
        } else {
            Toast.makeText(this, "当前用户尚未创建订单", Toast.LENGTH_SHORT).show();
        }


    }

    public void GoFirst(View btn) {
        Intent intent = new Intent(MineActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public Order transSeat(Seat seat) {
        Order order = new Order(1, null, null, null);
        if (seat.getSeatNumber().equals("future")) {
            order.setSeatNumber("已预约");
            order.setBookedBy(seat.getBookedBy());
            if (seat.getMovieID().equals("2131165319")) {
                order.setMovieID("电影四");
            } else if (seat.getMovieID().equals("2131165320")) {
                order.setMovieID("电影五");
            } else if (seat.getMovieID().equals("2131165321")) {
                order.setMovieID("电影六");
            }
        } else {
            order.setSeatNumber(seat.getSeatNumber());
            order.setBookedBy(seat.getBookedBy());
            if (seat.getMovieID().equals("2131165322")) {
                order.setMovieID("电影一");
            } else if (seat.getMovieID().equals("2131165323")) {
                order.setMovieID("电影二");
            } else if (seat.getMovieID().equals("2131165324")) {
                order.setMovieID("电影三");
            }

        }
        return order;
    }
}