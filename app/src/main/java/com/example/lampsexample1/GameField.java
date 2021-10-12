package com.example.lampsexample1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameField extends View {
    //расчетные переменные
    int n = 4;
    int r = 0;
    int m = 0;
    boolean[][] lampsStat = new boolean[n][n];

    //переменные для рисования
    Paint paint = new Paint();

    boolean isCalc = false;
    double touchX = 0, touchY = 0;//точки касания экрана

    public GameField(Context context) {
        super(context);
        paint.setColor(Color.BLUE);
        Random random = new Random();
        for (int i = 0; i < lampsStat.length; i++) {
            for (int j = 0; j < lampsStat[i].length; j++) {
                lampsStat[i][j] = random.nextBoolean();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(!isCalc){
            r = (int)(canvas.getWidth() / n * 0.75 /2);
            m = (int)(canvas.getWidth() / n * 0.25);
        }
        float x, y;
        y = m / 2 + r;
        for (int i = 0; i < lampsStat.length; i++) {
            x = m / 2 + r;
            for (int j = 0; j < lampsStat[i].length; j++) {
                if (lampsStat[i][j]){
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, r, paint);
                }else {
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(x, y, r, paint);
                }
                x += (m + 2 * r);
            }
            y += (m + 2 * r);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            checkLamp();
        }
        return super.onTouchEvent(event);
    }

    private void checkLamp(){
        int i, j;
        double x0, y0;
        i = (int)touchY / (m + 2 * r);
        j = (int)touchX / (m + 2 * r);
        x0 = j * (2 * r + m) + m / 2 + r;
        y0 = i * (2 * r + m) + m / 2 + r;
        if(Math.pow(touchX - x0, 2) + Math.pow(touchY - y0, 2) <= r * r ){
            lampsStat[i][j] = !lampsStat[i][j];
        }
        invalidate();
    }
}
