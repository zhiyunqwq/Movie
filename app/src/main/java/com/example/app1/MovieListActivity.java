package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/*public class MovieListActivity extends AppCompatActivity {
    private static final String TAG = "RateLisetActivity2";

    ListView listView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        listView = findViewById(R.id.listView);

        String[] initdata = {"11", "todo..."};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, initdata);
        //绑定
        listView.setAdapter(adapter);

        handler = new Handler(Looper.myLooper()){
            public void handleMessage(@NonNull Message msg){
                //处理返回
                if(msg.what==5){
                    Bundle bundle =(Bundle) msg.obj;
                    ArrayList<String> retlist = bundle.getStringArrayList("mylist");
                    ListAdapter adapter = new ArrayAdapter<String>(MovieListActivity.this,android.R.layout.simple_list_item_1,retlist);

                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void onclick(View btn){
        //启动线程
        Thread t = new Thread(new ListTask(handler));
        t.start();
        Log.i(TAG, "onclick: 线程启动");
    }*/
public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        recyclerView = findViewById(R.id.rvMovieList);
        movies = new ArrayList<>();
        // 填充电影数据...

        adapter = new MovieAdapter(this, movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 设置点击事件
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 跳转到电影详情页面
                Movie movie = movies.get(position);
                Intent intent = MovieDetailActivity.newIntent(MovieListActivity.this, movie);
                startActivity(intent);
            }
        });
    }
}