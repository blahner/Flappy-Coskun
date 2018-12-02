//created by Josh on 12/1/18
package com.example.josh.flappycoskun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private com.example.josh.flappycoskun.MainThread thread; //note: NOT android.support.annotation.MainThread, but a custom MainThread class which extends Thread
    private Birdy birdy;
    public PipeClass pipe1, pipe2, pipe3;
    public static int velocity = 10; //speed at which bird moves in x (or, conversely, the speed at which background moves toward bird)
    public static int gapHeight = 500; //space of gap between pipes

    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new com.example.josh.flappycoskun.MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new com.example.josh.flappycoskun.MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(true){ //keep looping until it works
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    //some info on bitmaps & canvases: http://www.informit.com/articles/article.aspx?p=2143148&seqNum=2
    public Bitmap resizeBitmap(Bitmap bmp, int newWidth, int newHeight){
        //returns a NEW bitmap similar to bmp (the input argument), but resized.
        //This is for drawing the various pipes
        int oldWidth = bmp.getWidth();
        int oldHeight = bmp.getHeight();

        //resizing
        Matrix matrix = new Matrix();
        matrix.postScale(((float) newWidth) / oldWidth, ((float) newHeight) / oldHeight);

        Bitmap resizedBMP = Bitmap.createBitmap(bmp, 0, 0, oldWidth, oldHeight, matrix, false);
        bmp.recycle(); //free memory (https://developer.android.com/reference/android/graphics/Bitmap#recycle())
        return resizedBMP;
    }

    private void createLevel() {
        //initializing objects
        birdy = new Birdy(/*BIRD IMAGE*/);
        Bitmap bmp;
        Bitmap bmp2;

        bmp = resizeBitmap(...);//BOTTOM PIPE IMAGE
        bmp2 = resizeBitmap(...);//TOP PIPE IMAGE

        //three pipes onscreen at a time
        pipe1 = new PipeClass(bmp, bmp2, , );
        pipe2 = new PipeClass(bmp, bmp2, , );
        pipe3 = new PipeClass(bmp, bmp2, , );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //on user touch, make the bird increase in height
        birdy.y = birdy.y - (birdy.yVelocity * 10);
        return super.onTouchEvent(event);
    }

    public void update(){
        logic();
        Birdy.update(); //called from a static context??
    }

    public void logic(){
        //detect if character touched a pipe (reset level if they are)
    }

    @Override
    public void draw(Canvas canvas){
        //display game
        super.draw(canvas);
        if(canvas != null){
            canvas.drawRGB(0,0,100); //full blue background
            //draw character
            Birdy.draw(canvas); //error: non-static method draw(Canvas) cannot be referenced from a static context
            //draw pipes
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);
        }
    }
}
