package com.example.app0214;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

//뷰를 상속받아 그래픽 처리를 개발자가 주도해보자
public class GalleryView extends View {
    Context context; //이 뷰를 관리하는 액티비티
    Bitmap[] bitmaps=new Bitmap[7];
    int index; //사진 배열을 접근할 인덱스

    //우리가 만든 뷰를,  xml에 올려놓을때는 해당 .xml에 사용된 태그 속성도
    //가져와야 하므로, 이때 이 태그속성을 받아들이는 객체가 바로 AttributeSet 이다
    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    //비트맵 배열을 미리 만들어놓기
    public void init(){
        bitmaps[0]=BitmapFactory.decodeResource(context.getResources(), R.drawable.img0);
        bitmaps[1]=BitmapFactory.decodeResource(context.getResources(), R.drawable.img1);
        bitmaps[2]=BitmapFactory.decodeResource(context.getResources(), R.drawable.img2);
        bitmaps[3]=BitmapFactory.decodeResource(context.getResources(), R.drawable.img3);
        bitmaps[4]=BitmapFactory.decodeResource(context.getResources(), R.drawable.img4);
        bitmaps[5]=BitmapFactory.decodeResource(context.getResources(), R.drawable.img5);
        bitmaps[6]=BitmapFactory.decodeResource(context.getResources(), R.drawable.img6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //이미지를 그리자
        canvas.drawBitmap(bitmaps[index], 50, 50, null);
    }

    //다음 이미지
    public void nextImg(){
        index++;
        invalidate(); //다시 그리기  java repaint()와 동일
    }

//이전 이미지
}




