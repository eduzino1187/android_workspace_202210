package com.example.listapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> list=new ArrayList<String>();
    GridView gridView;
    EditText t_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);

        gridView=(GridView)findViewById(R.id.gridView);
        list.add("바나나");
        list.add("사과");
        list.add("딸기");
        list.add("체리");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        gridView.setAdapter(adapter);

        //t_input= findViewById(R.id.t_input);
        //Button bt_regist = (Button)findViewById(R.id.bt_regist);
/*

        bt_regist.setOnClickListener((view)->{
            String item = t_input.getText().toString();
            regist(item);
        });
*/

    }
    public void regist(String item){
        list.add(item);
        //listView.notify();
    }
}