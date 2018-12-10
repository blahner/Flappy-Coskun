//created by Josh on 12/1/18
package com.example.josh.flappycoskun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private com.example.josh.flappycoskun.MainThread thread; //note: NOT android.support.annotation.MainThread, but a custom MainThread class which extends Thread
    private Birdy birdy;
    private background back;
    public PipeClass pipe1, pipe2;
    private static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public ArrayList<PipeClass> pipes = new ArrayList<>(2);
    public int score; //updated every time a pipe travels off-screen
    public boolean inGame = false;
    public int velocity = 12; //speed at which bird moves in x (or, conversely, the speed at which background moves toward bird)
    //initialize background colors
    int red = 100;
    int green = 0;
    int blue = 0;
    int counter = 0;
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

    private void createLevel() {
        score = 0;
        velocity = 12;

        Bitmap tempscene = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundone);
        Bitmap scene = PhotoHandler.resizeBitmap(tempscene, background.width, background.height);
        back = new background(scene);
        //initializing objects
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.soxbird);
        birdy = new Birdy(PhotoHandler.resizeBitmap(b, Birdy.width, Birdy.height));

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.greenlinewall);
        bmp = PhotoHandler.resizeBitmap(bmp, PipeClass.width, PipeClass.height); //TOP PIPE IMAGE

        //initialize all three pipes
        pipe1 = new PipeClass(bmp, bmp, screenWidth);
        pipes.add(pipe1);
        pipe2 = new PipeClass(bmp, bmp, screenWidth + screenWidth/2);
        pipes.add(pipe2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(!inGame){
            inGame = true;
        }else {
            //on user touch, make the bird increase in height
            birdy.velocity = 28;
            //playFlap();
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        counter++;
        if(counter%2 == 0) {
            logic();
            back.update(inGame, velocity/2);
        }
        else {
            birdy.update(inGame);
            for (PipeClass p : pipes) {
                p.update(inGame, velocity);
            }
        }
        if(back.getxPos() + background.width < screenWidth) {
            back.setxPos(0);
        }
    }

    public void logic(){
        //has character hit top or bottom of screen?
        if(birdy.y < 0) {
            resetLevel();
        }
        if(birdy.y > screenHeight){
            resetLevel();
        }

        for(PipeClass p : pipes) {
            if( (birdy.x > p.getxPos() && birdy.x < p.getxPos() + PipeClass.width) || (birdy.x + Birdy.width > p.getxPos() && birdy.x + Birdy.width < p.getxPos() + PipeClass.width)) {
                if (birdy.y < p.getyPos() + PipeClass.height) {
                    //colliding with top pipe
                    resetLevel();
                }
                if (birdy.y + Birdy.height > p.getyPos() + PipeClass.height + PipeClass.gapSpacing) {   //colliding with bottom pipe
                    resetLevel();
                }
            }
            if (p.getxPos() < 0) {
                //pipe has travelled off screen
                p.setxPos(screenWidth);
                p.resetyPos();
                score++;
                velocity = 12 + score/10; //increase speed
            }
        }
    }

    public void resetLevel(){
        score = 0;
        back.setxPos(0);
        inGame = false;

        pipe1.setxPos(screenWidth);
        pipe2.setxPos(screenWidth + screenWidth/2);
        pipe1.resetyPos();
        pipe2.resetyPos();

        birdy.y = screenHeight/2;
        birdy.velocity = 0;

        //background
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
            //these 3 lines were an attempt to get the background right. Causes some serious lag though.
            //Bitmap background = PhotoHandler.decodeResource(getResources(), R.drawable.backgroundone, screenWidth/4, screenHeight/16);
            //Paint paintb = new Paint();
            //canvas.drawBitmap(PhotoHandler.resizeBitmap(background, screenWidth*4, screenHeight), 0, 0, paintb);
            //canvas.drawRGB(red, blue, green);
            //draw character
            back.draw(canvas);
            birdy.draw(canvas);
            //draw pipes
            for(PipeClass p : pipes)
                p.draw(canvas);
        }
        drawScore(canvas, score);

        if(!inGame){
            //draw the "tap to begin" prompt
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText("tap to begin", screenWidth/2, screenHeight/2 + 100, paint);
        }
    }

    public void drawScore(Canvas canvas, int num){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText(Integer.toString(num), screenWidth/2 - 50, 100, paint);
    }


    /*
    private void playFlap(){
        Resources res = getResources();
        int id = res.getIdentifier("", "id", getContext().getPackageName());
        MediaPlayer mp = MediaPlayer.create(this, com.example.josh.flappycoskun.R.sounds);

    }
    */
}
