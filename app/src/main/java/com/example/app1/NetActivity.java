package com.example.app1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NetActivity extends AppCompatActivity implements Runnable{
    private static final String TAG = "Net";
    Handler handler;
    TextView show;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_net);
        show = findViewById(R.id.net_show);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                //处理返回
                if(msg.what==5){
                    String str=(String) msg.obj;
                    Log.i(TAG,"handleMessage:str=" + str);
                    show.setText(str);
                }
                super.handleMessage(msg);
            }
        };

    }

    public void onClick_net(View btn){
        Log.i(TAG,"onCreate:start Thread");
        Thread t=new Thread(this);
        t.start();
    }
    @Override
    public void run(){
        Log.i(TAG,"run:子线程run()......");

        //获取网络数据
        URL url=null;
        String html = "";
        try{
//            url = new URL("https://www.boc.cn/sourcedb/whpj/index_1.html");
//            HttpURLConnection http = (HttpURLConnection)url.openConnection();
//            InputStream in = http.getInputStream();
//
//            html = inputStream2String(in);
//            Log.i(TAG,"run:html ="+html);
            Document doc = Jsoup.connect("https://wocha.cn/huilv/?jinri").get();
            Elements tables = doc.getElementsByTag("table");
            org.jsoup.nodes.Element table = tables.get(0);
            Elements rows=table.getElementsByTag("tr");
            rows.remove(0);
            for(org.jsoup.nodes.Element row : rows){
                Log.i(TAG,"run:row="+row);
                Elements tds = row.getElementsByTag("td");
                if (!tds.isEmpty()){
                    org.jsoup.nodes.Element td1 = tds.get(0);
                    if(tds.size()>=5) {
                        Element td2 = tds.get(4);

                        String str1 = td1.text().trim();
                        String str2 = td2.text().trim();

                        Log.i(TAG,"run:td1 = "+ str1 +"->" + str2);

                        //html方法会保留文本+HTML标签
                        html += (str1 +"->" + str2 +"\n");

                    }
                }

            }
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }


        //发送消息
        Message msg = handler.obtainMessage(5,html);
        handler.sendMessage(msg);

    }

   //private String inputStream2String(InputStream inputStream)throws IOException{
   //    final int bufferSize =1024;
   //    final char[] buffer = new char[bufferSize];
   //    final StringBuilder out = new StringBuilder();
   //    Reader in = new InputStreamReader(inputStream,"utf-8");
   //    while(true) {
   //        int rsz = in.read(buffer, 0, buffer.length);
   //        if (rsz < 0) {
   //            break;
   //            out.append(buffer, 0, rsz);
   //        }
   //        return out.toString();
   //    }
   //}
}