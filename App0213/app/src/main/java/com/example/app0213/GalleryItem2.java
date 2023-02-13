package com.example.app0213;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

//안드로이드의 뷰들간의 디자인을 순수 자바코드로 작성하면, 개발은 가능하지만
//효율성이 너무 떨어진다..따라서 디자인은  xml로 작성하고, 이 작성된  xml을
//인플레이션 시켜보자...
public class GalleryItem2 {
    RelativeLayout layout;
    public GalleryItem2(Context context, String  title) {
         //이미 xml로 디자인된 파일이 있으므로, LayoutInflator 를 이용하여
        // xml에 명시된 태그들을 실제 안드로이드 뷰로 생성해보자
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout=(RelativeLayout)layoutInflater.inflate(R.layout.gallery_item, null, false);

        //인플레이션 된 뷰들중 아이디를 이용하여 접근하기
        TextView t_title=layout.findViewById(R.id.t_title);
        t_title.setText(title); //제목대입

    }

}






