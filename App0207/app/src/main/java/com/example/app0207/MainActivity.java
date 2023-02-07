package com.example.app0207;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG=this.getClass().getName();

    /*onCreate 란 액티비티 클래스의 생명주기 메서드 중, 초기화를 담당하는
    메서드이다....*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //개발자는, 안드로이드 화면을 구성할 UI컴포넌 트를 직접 자바 코드로
        //작성할 수도 있지만, 개발의 효율성이 떨어지므로, 특별한 이유가 아니라면
        //xml 에서의 작업이 효율성이 높다..아래의 setContentView() 메서드는
        //개발자가 자바코드 대신 태그 형식으로, 작성한 디자인 코드를 해석하여
        //실제 안드로이드의 컴포넌트로 메모리에 올려준다
        //html,javascript를경험한 개발자라면 DOM을 제어하듯이 개발하면 된다..
        //따라서 이벤트를 구현하려면 문서가 모두 읽혀진 이후에 접근해야 한다..
        setContentView(R.layout.reltest);

        //메모리에 올라온 컴포넌트 접근하기..document.getElementById()
        Button bt_login=(Button)this.findViewById(R.id.bt_login);
        Button bt_join=(Button)this.findViewById(R.id.bt_join);
        Button bt_find=(Button)this.findViewById(R.id.bt_find);

        //버튼과 리스너 연결
        bt_login.setOnClickListener(this);
        bt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "가입을 원해?");
            }
        });
        bt_find.setOnClickListener((v)->{
            Log.d(TAG, "찾기를 원해?");
        });
    }

    //javaSE actionPerform()처럼 추상메서드 오버라이드...
    //이때 매개변수로 넘어오는  View는 이벤트를 일으킨 뷰인 버튼이다..
    @Override
    public void onClick(View view) {
        Log.d(TAG, "눌렀어?");
    }
}