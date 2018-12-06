//Created by Josh on 11/19/18
package com.example.josh.flappycoskun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// import android.app.Activity;
// import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
// import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //no toolbar
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //fullscreen the display
        setContentView(new com.example.josh.flappycoskun.GameView(this)); //GameView class defined in GameView.java file
        //com.example.josh.flappycoskun.GameView.createLevel();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
