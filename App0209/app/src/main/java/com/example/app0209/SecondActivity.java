package com.example.app0209;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //xml 읽기
        setContentView(R.layout.grid_view);

        GridView gridView=(GridView)this.findViewById(R.id.gridView);
        ArrayList<String> list = new ArrayList<String>();
        list.add("포테이토칲");
        list.add("빼빼로");
        list.add("썬칩");
        list.add("홈런볼");
        list.add("꼬깔콘");

        ArrayAdapter<String> adapter=null;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        gridView.setAdapter(adapter);

    }
}




