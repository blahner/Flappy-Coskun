package com.example.josh.flappycoskun;

//Created by Josh Bone - 12/1/18

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.SensorManager;

class Birdy {
    private Bitmap image;
    public int x = 100;
    public int y = 700; //character position
    public static int width = 150;
    public static int height = 150;
    public int gravity = 1;
    public int velocity = 0;
    int delay = 20;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Birdy(Bitmap bmp){
        image = bmp;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public void update(){
        if(delay > 0){
            delay--;
        } else {
            velocity -= gravity;
            y -= velocity;
        }
    }
}
