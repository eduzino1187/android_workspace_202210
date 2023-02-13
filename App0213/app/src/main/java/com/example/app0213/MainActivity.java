package com.example.app0213;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG=this.getClass().getName();
    EditText t_input;
    List nationList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // xml 을 반드시 써야하는것은 아니지만, 스윙처럼 직접 자바코드만으로
        //디자인을 할 경우 효율성이 떨어진다..따라서 xml 은 사용빈도가 높다
        setContentView(R.layout.activity_main);
        // xml로부터 뷰객체들이 태어나는 과정을  inflation 이라 한다
        //setContentView 메서드 호출 이후부터는  id 만 알면, 인스턴스를 접근할 수 있다..
        //이때 아이디를 통해 접근하는 메서드가  findViewById 이다(getElementById 와 흡사)
        Button bt_regist = (Button)findViewById(R.id.bt_regist);
        t_input = (EditText)findViewById(R.id.t_input);

        //xml문서에 있는 ListView 를 제어하기 위해  id 를 이용하여 레퍼런스 얻기
        ListView listView=(ListView)this.findViewById(R.id.listView);

        //List, GridView  일명 어댑터를 이용한다 하여   어댑터뷰라 하는데,
        //주로 목록을 처리하는데 압도적으로 많이 사용됨..
        //swing에서의  JTable 이 TableModel을 이용하여 데이터를 연동하는것과 동일
        //  JTable - ListView, GridView  등의 어댑터뷰
        //  TableModel - Adapter
        nationList=new ArrayList();
        nationList.add("벨기에");
        nationList.add("튀르키에");
        nationList.add("소말리아");
        nationList.add("이디오피아");
        nationList.add("레바논");
        nationList.add("태국");

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nationList);
        listView.setAdapter(adapter);


        //버튼과 리스너 연결
        bt_regist.setOnClickListener((v)->{
            Log.d(TAG ,"눌럿어?");
            regist();
        });
    }

    public void regist(){
        //입력창에 입력한 값을  List에 추가하자!!
        String value=t_input.getText().toString();
        nationList.add(value);

        //어댑터 새로고침
        adapter.notifyDataSetChanged();
    }
}




