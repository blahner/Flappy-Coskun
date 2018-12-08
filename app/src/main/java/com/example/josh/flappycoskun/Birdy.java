package com.example.josh.flappycoskun;

//Created by Josh Bone - 12/1/18

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

class Birdy {
    private Bitmap image;
    static int width = 150;
    public static int height = 150;
    public double gravity = 0.8;
    public double velocity = 0;
    //int delay = 20;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public int x = 100;
    public int y = screenHeight/2; //character position

    public Birdy(Bitmap bmp){
        image = bmp;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public void update(int gameState){
        if(gameState == 1){
            velocity -= gravity;
            y -= velocity;
        }
    }
}
