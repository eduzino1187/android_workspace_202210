package com.example.app0214;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

//개발자 주도하에 그림 그리기
//자바SE - paint(), android- onDraw()
public class MyView extends View {
    int x=50;
    int y=50;
    int width=100; //right
    int height=100;//bottom

    int velX=2;
    int velY=2;

    Thread thread; //게임 루프 쓰레드


    //안드로이드의 모든 뷰는 반드시 어느 액티비티에서 관리되는지 그 소속관
    //계를 명시해야 한다..따라서 생성자에 액티비티를 넘겨받아야 한다..
    public MyView(Context context) {
        super(context);

        thread = new Thread(){
            public void run() {
                while(true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    tick();
                    render();
                }
            }
        };

        thread.start();
    }

    //java swing의  paint(Graphics g) 메서드와  동일
    //자바에서는  Graphics 로 그래픽 처리를 하지만, 안드로이드에서는 명칭이
    //Canvas로 바뀜 , 주의  java.awt.Canvas와는 틀리다 , 즉 Graphics에 가까움
    protected void onDraw(Canvas canvas) {
        //페인트통 만들기
        Paint paint =new Paint();
        paint.setColor(Color.RED);

        //사각형만들기
        Rect rect = new Rect(x, y, x+width, y+height);
        canvas.drawRect(rect , paint);
    }

    //물리량 변화
    public void tick(){
        x=x+velX;
        y=y+velY;
    }

    //화면에 반영
    public void render(){
        invalidate(); //onDraw() 다시 그리게 호출...
    }
}










