package com.example.noticeclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DetailActivity extends AppCompatActivity {
    private String TAG=this.getClass().getName();

    EditText t_title, t_writer, t_content;
    int notice_idx;
    Handler handler;
    Notice notice;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //나를 호출한 액티비티가 혹여나,  Intent 안에 뭔가를 넣었다면
        //꺼내먹자!!
        Intent intent=this.getIntent(); //새로운 인텐트가 아니라, 다른 액티비티에서
        //전달한 인텐트이다!!!
        notice_idx=intent.getIntExtra("notice_idx", 0);


        t_title=findViewById(R.id.t_title);
        t_writer=findViewById(R.id.t_writer);
        t_content=findViewById(R.id.t_content);

        Button bt_edit = findViewById(R.id.bt_edit);
        Button bt_del = findViewById(R.id.bt_del);
        Button bt_list = findViewById(R.id.bt_list);

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                //메인쓰레드에 의해 수행된다, 따라서 디자인 제어가 가능하다
                t_title.setText(notice.getTitle());
                t_writer.setText(notice.getWriter());
                t_content.setText(notice.getContent());
            }
        };
    }

    public void  convertJsonToObject(JSONObject json){
        // 멤버변수의 dTO에 값을 채워놓고, 핸들러에게 접근하도록 하자
        notice = new Notice();
        try {
            notice.setNotice_idx(json.getInt("notice_idx"));
            notice.setTitle(json.getString("title"));
            notice.setWriter(json.getString("writer"));
            notice.setContent(json.getString("content"));
            notice.setRegdate(json.getString("regdate"));
            notice.setHit(json.getInt("hit"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public void getDetail(){
        //GET방식의 요청시도 !! json 으로 가져오기
        BufferedReader buffr=null;
        InputStreamReader reader=null;

        try {
            URL url = new URL("http://172.30.1.27:7777/rest/notice/detail?notice_idx="+notice_idx);
            URLConnection uCon=url.openConnection();
            HttpURLConnection httpCon=(HttpURLConnection) uCon;
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);

            int code=httpCon.getResponseCode(); //200, 404, 500...
            Log.d(TAG, "서버의 응답정보"+code);

            if(code == HttpURLConnection.HTTP_OK){
                reader = new InputStreamReader(httpCon.getInputStream(),"UTF-8");
                buffr = new BufferedReader(reader);

                StringBuilder sb = new StringBuilder();
                String msg=null;
                while(true){
                    msg=buffr.readLine();
                    if(msg==null)break;
                    sb.append(msg);
                }
                Log.d(TAG, sb.toString());

                //파싱~~~~~~~

                //게시물이 1건이므로, 단수형  JSON이다
                JSONObject json = new JSONObject(sb.toString());
                convertJsonToObject(json);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally{
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if(buffr!=null){
                try {
                    buffr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }



    protected void onStart() {
        super.onStart();
        Thread thread = new Thread(){
            public void run() {
                getDetail();
            }
        };
        thread.start();
    }

}








