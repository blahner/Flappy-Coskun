package com.example.josh.flappycoskun;

//Created by Josh Bone - 12/1/18

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.SensorManager;

class Birdy {
    private Bitmap image;
    public int x, y; //character position
    private int xVelocity = 10;
    public int yVelocity = 10;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Birdy(Bitmap bmp){
        image = bmp;
        x = 100;
        y = 100;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public void update(){
        y += yVelocity;
    }
}
