package com.example.app1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    public List<Map<String,Object>> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public FilmAdapter(List<Map<String,Object>> list, Context context) {
        this.mList = list;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.activity_film_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(mList.get(position).get("img")).into(holder.mImgPic);
        holder.mTextFilmName.setText(mList.get(position).get("nm").toString());
        holder.mTextReleasedTime.setText(mList.get(position).get("rt").toString());
        holder.mTextScore.setText(mList.get(position).get("sc").toString());
        holder.mTextStarring.setText(mList.get(position).get("star").toString());
    }

    @Override
    public int getItemCount() {
        return mList.size(); //返回的数量
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgPic;
        TextView mTextFilmName;
        TextView mTextReleasedTime;
        TextView mTextScore;
        TextView mTextStarring;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgPic = itemView.findViewById(R.id.img_item_pic);
            mTextFilmName = itemView.findViewById(R.id.text_item_film_name);
            mTextReleasedTime = itemView.findViewById(R.id.text_item_released_time);
            mTextScore = itemView.findViewById(R.id.text_item_score);
            mTextStarring = itemView.findViewById(R.id.text_item_starring);
        }
    }
}
