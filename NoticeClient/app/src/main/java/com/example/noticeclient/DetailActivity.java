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
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DetailActivity extends AppCompatActivity {
    private String TAG=this.getClass().getName();

    EditText t_title, t_writer, t_content;
    int notice_idx;
    Handler handler;
    Handler handler2;

    Notice notice;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //나를 호출한 액티비티가 혹여나,  Intent 안에 뭔가를 넣었다면
        //꺼내먹자!!
        Intent intent=this.getIntent(); //새로운 인텐트가 아니라, 다른 액티비티에서
        //전달한 인텐트이다!!!
        notice_idx=intent.getIntExtra("notice_idx", 0);
        Log.d(TAG, "이 액티비티가 초기화될때 넘겨받은 notice_idx="+notice_idx);

        t_title=findViewById(R.id.t_title);
        t_writer=findViewById(R.id.t_writer);
        t_content=findViewById(R.id.t_content);

        Button bt_edit = findViewById(R.id.bt_edit);
        Button bt_del = findViewById(R.id.bt_del);
        Button bt_list = findViewById(R.id.bt_list);

        //수정하기
        bt_edit.setOnClickListener((v)->{
            Thread thread = new Thread(){
                public void run() {
                    edit();
                }
            };
            thread.start();
        });

        //삭제하기
        bt_del.setOnClickListener((v)->{
            Thread thread=new Thread(){
                public void run() {
                    del();
                }
            };
            thread.start();
        });

        //목록보기
        bt_list.setOnClickListener((v)->{
            goList();
        });


        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                //메인쓰레드에 의해 수행된다, 따라서 디자인 제어가 가능하다
                t_title.setText(notice.getTitle());
                t_writer.setText(notice.getWriter());
                t_content.setText(notice.getContent());
            }
        };
        handler2 = new Handler(Looper.getMainLooper()){
            public void handleMessage(@NonNull Message msg) {
                goList();
            }
        };

    }
    public void goList(){
        this.finish();
    }
    public void del(){
        //GET방식의 요청시도 !! json 으로 가져오기
        BufferedReader buffr=null;
        InputStreamReader reader=null;

        try {
            URL url = new URL("http://172.30.1.27:7777/rest/notice/del?notice_idx="+notice_idx);
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
                //JSONObject json = new JSONObject(sb.toString());
                //convertJsonToObject(json);

                handler2.sendEmptyMessage(0);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }  finally{
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

            //화면에 반영하기(네트워크 통신은 개발자 정의 쓰레드에 의해 진행되었으므로
            // 화면에 반영하는 권한은 main쓰레드에 있기 때문에 핸들러에 의한 간접 제어.)
            handler.sendEmptyMessage(0);

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

    //수정하기
    public void edit(){
        // Http Request
        BufferedWriter buffw=null;
        OutputStreamWriter os=null;
        DataOutputStream dos=null;

        BufferedReader buffr=null;
        InputStreamReader is=null;

        try {
            URL url = new URL("http://172.30.1.27:7777/rest/notice/edit");
            URLConnection uCon=url.openConnection();
            HttpURLConnection httpCon=(HttpURLConnection) uCon;
            httpCon.setRequestMethod("POST");
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //보낼 데이터
            String title=t_title.getText().toString();
            String writer=t_writer.getText().toString();
            String content=t_content.getText().toString();

            String postData="title="+title+"&writer="+writer+"&content="+content+"&notice_idx="+notice_idx;
            os = new OutputStreamWriter(httpCon.getOutputStream(), "UTF-8");
            buffw=new BufferedWriter(os);
            //dos = new DataOutputStream(httpCon.getOutputStream());
            //dos.writeBytes(postData);
            buffw.write(postData+"\n");
            buffw.flush();

            //데이터 요청 이후에 입력스트림 만들어야 한다..
            is = new InputStreamReader(httpCon.getInputStream(), "UTF-8");
            buffr = new BufferedReader(is);

            StringBuilder sb=new StringBuilder();
            while(true){
                String result=buffr.readLine();
                sb.append(result);
                if(result==null){
                    break;
                }
            }
            Log.d(TAG, sb.toString());
            //수정 성공시 성공메시지 출력 (Alert)

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(is!=null){
                try {
                    is.close();
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
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(buffw!=null){
                try {
                    buffw.close();
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








