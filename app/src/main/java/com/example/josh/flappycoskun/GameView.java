//created by Josh on 12/1/18
package com.example.josh.flappycoskun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static com.example.josh.flappycoskun.MainThread.canvas;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private com.example.josh.flappycoskun.MainThread thread; //note: NOT android.support.annotation.MainThread, but a custom MainThread class which extends Thread
    private Birdy birdy;
    public PipeClass pipe1, pipe2;
    public static int velocity = 10; //speed at which bird moves in x (or, conversely, the speed at which background moves toward bird)
    private static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public ArrayList<PipeClass> pipes = new ArrayList<>(2);

    //initialize background colors
    int red = 0;
    int green = 0;
    int blue = 1;

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
        createLevel();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){ //keep looping until it works
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
        //Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bird, null);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        birdy = new Birdy(resizeBitmap(b, Birdy.width, Birdy.height));

        //Drawable drawable1 = ResourcesCompat.getDrawable(getResources(), R.drawable.top_pipe, null);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.top_pipe);

        //Drawable drawable2 = ResourcesCompat.getDrawable(getResources(), R.drawable.bottom_pipe, null);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_pipe);
        bmp = resizeBitmap(bmp, PipeClass.width, PipeClass.height); //TOP PIPE IMAGE
        bmp2 = resizeBitmap(bmp2, PipeClass.width, PipeClass.height); //BOTTOM PIPE IMAGE

        //initialize all three pipes
        pipe1 = new PipeClass(bmp, bmp2, screenWidth - PipeClass.width, 0);
        pipes.add(pipe1);
        pipe2 = new PipeClass(bmp, bmp2, screenWidth - PipeClass.width - screenWidth/3, 0);
        pipes.add(pipe2);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //on user touch, make the bird increase in height
        birdy.y = birdy.y - (birdy.yVelocity * 20);
        return super.onTouchEvent(event);
    }

    public void update() {
        logic();
        birdy.update();
        for (PipeClass p : pipes){
            p.update();
        }
    }

    public void logic(){
        //has character hit top or bottom of screen? (if so, reset level)
        if(birdy.y < 0){
            resetLevel();
        }//in a later version, these should return user to a menu
        if(birdy.y > screenHeight){
            resetLevel();
        }
        //has character touched a pipe? (if so, reset level)
        for(PipeClass p : pipes) {
            if( (birdy.x > p.getxPos() && birdy.x < p.getxPos() + PipeClass.width) || (birdy.x + birdy.width > p.getxPos() && birdy.x + birdy.width < p.getxPos() + PipeClass.width))
                if(birdy.y < p.getyPos() + PipeClass.height && birdy.y > p.getyPos()) {
                //colliding with top pipe
                resetLevel();
                }
                if(birdy.y + birdy.height > p.getyPos() + PipeClass.height + PipeClass.gapSpacing)
                {   //colliding with bottom pipe
                    resetLevel();
                }
            if(p.getxPos() < 0)
                p.setxPos(screenWidth);//detect if one of the three pipes is gone, need another
        }
    }

    public void resetLevel(){
        //in a later update, we should try creating a menu where the user chooses to start game
        pipe1.setxPos(screenWidth - PipeClass.width);
        pipe2.setxPos(screenWidth - PipeClass.width - screenWidth/2);
        pipe1.resetyPos();
        pipe2.resetyPos();
        birdy.y = 700;
        Random rand = new Random();
        red = rand.nextInt();
        blue = rand.nextInt();
        green = rand.nextInt();
    }

    @Override
    public void draw(Canvas canvas){
        //display game
        super.draw(canvas);
        if(canvas != null){
            canvas.drawRGB(red,green,blue); //random background
            //draw character
            birdy.draw(canvas); //error: non-static method draw(Canvas) cannot be referenced from a static context
            //draw pipes
            for(PipeClass p : pipes)
                p.draw(canvas);
        }
    }
}
