package com.example.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Sprite {

    private Bitmap bitmap;
    private List<Rect> frames;
    private int frameWidth;
    private int frameHeight;
    private int padding;

    private int currentFrame;
    private double frameTime;
    private double timeForCurrentFrame;

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;

    public Sprite(Bitmap bitmap, int x, int y, double velocityX, double velocityY, Rect initialFrame) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.frames = new ArrayList<>();
        this.frames.add(initialFrame);
        this.timeForCurrentFrame = 0.0;
        this.currentFrame = 0;
        this.padding = 20;
        this.frameTime = 0.1;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public List<Rect> getFrames() {
        return frames;
    }

    public void setFrames(List<Rect> frames) {
        this.frames = frames;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame % frames.size();
    }

    public double getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(double frameTime) {
        this.frameTime = Math.abs(frameTime);
    }

    public double getTimeForCurrentFrame() {
        return timeForCurrentFrame;
    }

    public void setTimeForCurrentFrame(double timeForCurrentFrame) {
        this.timeForCurrentFrame = Math.abs(timeForCurrentFrame);
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getFrameCount(){
        return frames.size();
    }

    public void addFrame(Rect frame){
        frames.add(frame);
    }

    public void update(int ms){

        timeForCurrentFrame += ms;

        if (timeForCurrentFrame >= frameTime){
            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;
        }

        x = x + velocityX * ms/1000.0;
        y = y + velocityY * ms/1000.0;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        Rect destination = new Rect((int)x, (int)y, (int)(x+frameWidth), (int)(y+frameHeight));
        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination, paint);
    }
    
    public Rect getBoundingBoxRect(){
        return new Rect((int)(x+padding), (int)(y+padding),
                (int)(x+frameWidth-2*padding), (int)(y+frameHeight-2*padding));
    }

    public boolean intersect(Sprite sprite){
        return getBoundingBoxRect().intersect(sprite.getBoundingBoxRect());
    }
}
