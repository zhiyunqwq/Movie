package com.example.app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private List<Seat> seatList;
    private LayoutInflater inflater;
    private OnSeatClickListener onSeatClickListener;

    SeatAdapter(Context context, List<Seat> seatList) {
        this.seatList = seatList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SeatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.seat_item, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);
        holder.bind(seat);
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public void setOnSeatClickListener(OnSeatClickListener listener) {
        this.onSeatClickListener = listener;
    }

    class SeatViewHolder extends RecyclerView.ViewHolder {
        // Seat view components
        TextView seatNumberView;
        ImageView seatStatusView;

        SeatViewHolder(View itemView) {
            super(itemView);
            seatNumberView = itemView.findViewById(R.id.seat_number);
            seatStatusView = itemView.findViewById(R.id.seat_status);
            itemView.setOnClickListener(v -> {
                if (onSeatClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onSeatClickListener.onSeatClick(seatList.get(position));
                    }
                }
            });
        }

        void bind(Seat seat) {
            seatNumberView.setText(seat.getSeatNumber());
            // Update seat status image based on whether the seat is booked
            seatStatusView.setImageResource(seat.isBooked() ? R.drawable.seat_booked : R.drawable.seat_available);
        }
    }

    interface OnSeatClickListener {
        void onSeatClick(Seat seat);
    }
}