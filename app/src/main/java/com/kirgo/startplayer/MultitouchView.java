package com.kirgo.startplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MultitouchView extends View implements View.OnTouchListener{

    private Paint paint;
    private Paint paintText;
    private static final int SIZE = 200;
    CountDownTimer countDownTimer = null;
    private int[] colors = {Color.BLUE, Color.GREEN, Color.BLACK, Color.YELLOW,
            Color.LTGRAY, Color.MAGENTA, Color.CYAN, Color.GRAY, Color.DKGRAY, Color.RED};
//    private int x = 0;
//    private int y = 0;
    private int colorIndex = 0;

    ArrayList<Vector2D> map = new ArrayList<>();
    private int playerSelected = -1;

    //    Map <Integer, Vector2D> map = new HashMap<>();
    public MultitouchView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setARGB(255, 255, 0, 0);

        paintText = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(200);

        this.setOnTouchListener(this);
    }


    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (playerSelected == -1) {
            for (int i = 0; i < map.size(); i++) {
                int x = map.get(i).posX;
                int y = map.get(i).posY;
                int c = map.get(i).color;
                paint.setColor(c);
                canvas.drawCircle(x, y, SIZE, paint);
            }
//        for(Map.Entry<Integer, Vector2D> pair: map.entrySet()){
//            int x = pair.getValue().getPosX();
//            int y = pair.getValue().getPosY();
        }else {
            for (int i = 0; i < map.size(); i++) {
                int x = map.get(i).posX;
                int y = map.get(i).posY;
                if (i == playerSelected) {
                    paint.setColor(Color.RED);
                }else {
                    paint.setColor(Color.BLACK);
            }
//        }else{
//                paint.setColor(colors[index % colors.length]);
//            }
            canvas.drawCircle(x,y, SIZE, paint);
        }
    }
        canvas.drawText("1", 100, 200, paintText);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int maskedAction = event.getActionMasked();

        switch (maskedAction){
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:{
                int x = (int) event.getX(pointerIndex);
                int y = (int) event.getY(pointerIndex);
                Vector2D newPoint = new Vector2D(x, y, pointerID, colors[colorIndex% colors.length]);
                colorIndex++;
                map.add(newPoint);
//                map.put(pointerID, newPoint);
                if (map.size()> 1){
                    if (countDownTimer != null){
                        countDownTimer.cancel();
                    }
                    countDownTimer = new CountDownTimer(3000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            int min = 0;
                            int max = map.size();
                            Random random = new Random();
                            playerSelected = random.nextInt(max + min) + min;

                            invalidate();
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                        }
                    };
                    countDownTimer.start();

                }
            }
            break;
            case  MotionEvent.ACTION_MOVE:{
                for (int i = 0; i < map.size(); i++) {

                    boolean found = false;
//                int size = event.getPointerCount();
                    int index = 0;
                    while (!found && index < map.size()) {
                        if (map.get(index).ID == event.getPointerId(i)) {
                            map.get(index).posX = (int) event.getX(i);
                            map.get(index).posY = (int) event.getY(i);
                            found = true;
                        }
                        index++;
                    }
//                for (int i = 0; i < size; i++){
//                    Vector2D point = map.get(event.getPointerId(i));
//                    if (point != null){
//                        point.setPosX((int) event.getX(i));
//                        point.setPosY((int) event.getY(i));
//                    }
//                }
//                int x = (int) event.getX(pointerIndex);
//                int y = (int) event.getY(pointerIndex);
//                map.get(pointerID).setPosX(x);
//                map.get(pointerID).setPosY(y);
                }
            }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                if (countDownTimer != null){
                    countDownTimer.cancel();
                }
//                map.remove(pointerID);
                boolean found = false;
                int index = 0;
                while (!found && index < map.size()){
                    if (map.get(index).ID == pointerID){
                        map.remove(index);
                        found = true;
                    }
                    index ++;
                }
                break;
        }

        invalidate();
        return true;
    }
}
