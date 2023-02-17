package com.example.noticeclient;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 Swing 에서의 TableModel 과 거의 동일한 목적의 클래스 정의
*/
public class NoticeAdapter extends BaseAdapter {
    private String TAG=this.getClass().getName(); //로그 검색어로 사용할것임..
    //안드로이드 시스템에 너무나 많은 로그가 찍히므로, 필터를 적용하려 걸러내려면
    //필터 키워드가 잇어야 하는데, 현재 클래스명을 해당 키워드로 쓰기 위해...

    ListActivity listActivity;

    List<Notice> noticeList =new ArrayList<Notice>();
    LayoutInflater layoutInflater; //xml을 읽어들여  android view api 들을 생성해줌
                                                  //이 객체는 액티비티를 통해 얻을 수 있다..
    public NoticeAdapter(ListActivity listActivity) {
        this.listActivity = listActivity;
        layoutInflater = (LayoutInflater) listActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        test();
    }

    //db연동없이 테스트용으로..
    public void test(){
        for(int i=0;i<40;i++){
            Notice notice = new Notice();
            notice.setNotice_idx(i);
            notice.setTitle(i+"번째 제목입니다 ");
            notice.setWriter(i+"번째 작성자");
            notice.setRegdate("2022-02-"+i);

            noticeList.add(notice);
        }

    }

    //아이템의 수
    public int getCount() {
        return noticeList.size();
    }

    //지정된 인덱스의 데이터 하나 반환  Notice
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    //아이템에 부여할 고유값 (  idx를 활용 )
    public long getItemId(int i) {
        Notice notice = noticeList.get(i);
        return notice.getNotice_idx();
    }

    //해당 아이템이 화면에 등장할때 호출되는 메서드이며, 이 메서드를
    //어떻게 사용하느냐에 따라 앱의 성능이 좌우된다..
    //viewGroup의 정체 : 우리가 사용중인 ListView
    public View getView(int i, View view, ViewGroup viewGroup) {
        /*
        1) getView() 메서드는 언제 호출하는가? 가려져 있던 아이템이 등장할때
        2)여기서 return 해야할 View는 무엇인가? ListView의 한 아이템을 표현하는 뷰가
             와야 하고, 우리는  item_notice.xml 파일로 디자인을 해놓았다.. 하지만 JAVA 가
             아니므로, 그  xml을 읽어들여 객체화 시켜야 한다(인플레이션)
        * */

        Log.d(TAG, "i = "+i+", view = "+view+", viewGroup"+viewGroup);

        //매개변수로 넘겨받은  view 가  null인 경우만 인플레이션 시키고,
        //이미 태어만 뷰가 넘어온 기존의  넘어온 view를 그대로 이용하자

        //레코드 수만큼 인플레이션 시키기!!!
        if(view ==null) { //한번도 화면에 등장한 적이 없는 뷰라면 개발자가 생성
            view= layoutInflater.inflate(R.layout.item_notice, viewGroup, false);
        }

        Button bt_view = view.findViewById(R.id.bt_view);
        bt_view.setText(i + "");


        TextView t_title=view.findViewById(R.id.t_title);
        TextView t_writer=view.findViewById(R.id.t_writer);
        TextView t_regdate=view.findViewById(R.id.t_regdate);

        Notice notice=noticeList.get(i);

        bt_view.setOnClickListener((v)->{
            Log.d(TAG, "당신이 선택한 글의  idx는 "+notice.getNotice_idx());
            //상세 페이지를 담당하는 액티비티 호출 (new 불가 , 서블릿과 동일)
            Intent intent = new Intent( listActivity, DetailActivity.class);
            intent.putExtra("notice_idx", notice.getNotice_idx());
            listActivity.startActivity(intent);

        });

        t_title.setText(notice.getTitle());
        t_writer.setText(notice.getWriter());
        t_regdate.setText(notice.getRegdate());

        return view;
    }
}
