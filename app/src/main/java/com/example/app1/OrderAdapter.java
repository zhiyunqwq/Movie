package com.example.app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;
    private LayoutInflater inflater;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.inflater = LayoutInflater.from(context);
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        // 检查 orderList 是否为空或 position 是否有效
        if (orderList != null && position >= 0 && position < orderList.size()) {
            Order order = orderList.get(position);
            holder.bind(order);
        } else {
            // 处理空列表或无效位置的情况
        }
    }

    @Override
    public int getItemCount() {
        // 检查 orderList 是否为空
        return orderList != null ? orderList.size() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSeatNumber;
        TextView textViewMovieTitle;

        public OrderViewHolder(View itemView) {
            super(itemView);
            // 初始化视图组件
            textViewSeatNumber = itemView.findViewById(R.id.textViewSeatNumber);
            textViewMovieTitle = itemView.findViewById(R.id.textViewMovieTitle);
        }

        public void bind(Order order) {
            // 确保 textViewSeatNumber 和 textViewMovieTitle 已初始化
            if (textViewSeatNumber != null && textViewMovieTitle != null) {
                textViewSeatNumber.setText(order.getSeatNumber());
                textViewMovieTitle.setText(order.getMovieTitle());
            }
        }
    }
}