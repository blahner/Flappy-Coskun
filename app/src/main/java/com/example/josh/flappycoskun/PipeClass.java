package com.example.josh.flappycoskun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.josh.flappycoskun.GameView;

import static com.example.josh.flappycoskun.GameView.*;

class PipeClass {

    private Bitmap image; //top
    private Bitmap image2; //bottom
    private int yPos, xPos;
    static int width = 200;
    public static int height = Resources.getSystem().getDisplayMetrics().heightPixels; //screenheight (for good measure)
    public static int gapSpacing = 600; //space of gap between pipes

    public PipeClass (Bitmap bmp, Bitmap bmp2, int x){ //constructor
        image = bmp;
        image2 = bmp2;
        resetyPos();//position of top pipe (note the pipes hang off of the top of the screen)
        xPos = x; //left corner
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xPos, yPos, null); //draw the top pipe
        canvas.drawBitmap(image2, xPos, yPos + height + gapSpacing, null); //draw the bottom pipe
    }

    public void update(int inGame, double velocity){
        if(inGame == 1)
            xPos -= velocity; //move pipes in x-dir with time
    }
    public int getxPos(){
        return xPos;
    }
    public int getyPos(){
        return yPos;
    }
    public void resetyPos(){
        yPos = - height + (int) (Math.random() * (height/2) );
    }
    public void setxPos(int x){
        xPos = x;
    }
}
