package com.example.josh.flappycoskun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.josh.flappycoskun.GameView;

import static com.example.josh.flappycoskun.GameView.*;

class PipeClass {

    private Bitmap image; //top
    private Bitmap image2; //bottom
    public int yPos, xPos;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public PipeClass (Bitmap bmp, Bitmap bmp2, int x, int y){ //constructor
        image = bmp;
        image2 = bmp2;
        yPos = y; //top
        xPos = x; //left
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xPos, yPos -(gapHeight / 2), null); //draw the bottom pipe
        canvas.drawBitmap(image2, xPos, yPos + (GameView.gapHeight / 2) + (screenHeight / 2), null); //draw the top pipe
    }
    public void update(){
        xPos -= velocity; //move pipes in x-dir with time
    }
}
