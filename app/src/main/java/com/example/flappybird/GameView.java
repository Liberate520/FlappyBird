package com.example.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private int viewWidth;
    private int viewHeight;
    private int point = 0;
    private Sprite player;
    private Sprite enemy;
    private final int timeInterval = 30;

    public GameView(Context context) {
        super(context);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        int w = bitmap.getWidth()/5;
        int h = bitmap.getHeight()/3;

        Rect rect = new Rect(0,0, w, h);

        player = new Sprite(bitmap, 10, 0, 0, 100, rect);

        for (int i = 0; i<3; i++){
            for (int j = 0; j<5; j++){
                if (i==0 && j==0){
                    continue;
                }
                if (i==2 && j==4){
                    continue;
                }
                player.addFrame(new Rect(j*w, i*h, j*w+w, i*h+h));
            }
        }

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        w = bitmap.getWidth()/5;
        h = bitmap.getHeight()/3;
        rect = new Rect(4*w,0, 5*w, h);

        enemy = new Sprite(bitmap, 2000, 250, -300, 0, rect);

        for (int i = 0; i<3; i++){
            for (int j = 4; j>=0; j--){
                if (i==0 && j==4){
                    continue;
                }
                if (i==2 && j==0){
                    continue;
                }
                enemy.addFrame(new Rect(j*w, i*h, j*w+w, i*h+h));
            }
        }

        Timer timer = new Timer();
        timer.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawARGB(250, 127, 199, 255);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(55.0f);
        paint.setColor(Color.WHITE);
        canvas.drawText(point + "", viewWidth - 120, 70, paint);

        player.draw(canvas);
        enemy.draw(canvas);
    }

    public void update(){
        player.update(timeInterval);
        enemy.update(timeInterval);
        if (player.getY() + player.getFrameHeight() > viewHeight){
            player.setY(viewHeight-player.getFrameHeight());
            player.setVelocityY(-player.getVelocityY());
        }
        else if (player.getY() < 0){
            player.setY(0);
            player.setVelocityY(-player.getVelocityY());
        }
        if (enemy.getX() < -enemy.getFrameWidth()){
            teleportEnemy();
        }
        if (enemy.intersect(player)){
            teleportEnemy();
            point -= 300;
        }
        point++;
        invalidate();
    }

    private void teleportEnemy(){
        enemy.setX(viewWidth + (int)(Math.random()*500));
        enemy.setY((int)(Math.random() * (viewHeight - enemy.getFrameHeight())));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();

        if (eventAction == MotionEvent.ACTION_DOWN){

            if (event.getY() < player.getBoundingBoxRect().top){
                player.setVelocityY(player.getVelocityY()-50);
            }

            if (event.getY() > player.getBoundingBoxRect().top){
                player.setVelocityY(player.getVelocityY()+50);
            }
        }

        return true;
    }

    class Timer extends CountDownTimer{
        public Timer() {
            super(Integer.MAX_VALUE, timeInterval);
        }

        @Override
        public void onTick(long l) {
            update();
        }

        @Override
        public void onFinish() {

        }
    }
}
