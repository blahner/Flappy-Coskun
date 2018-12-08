package com.example.josh.flappycoskun;

//Main thread of execution for Flappy Coskun app.
//Youtube tutorial for thread in 2d game: https://www.youtube.com/watch?v=GMHK-Htyjy8
//created by Josh Bone - 12/1/18

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    public static Canvas canvas;
    private com.example.josh.flappycoskun.GameView gameView;

    public MainThread(SurfaceHolder surfaceHolder, com.example.josh.flappycoskun.GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run(){
    //run full-tilt
        while(running){
            //draw stuff
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }catch(Exception e){}
            finally{
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
        }
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }
}
