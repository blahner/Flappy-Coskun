//Created by Josh on 11/19/18
package com.example.josh.flappycoskun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// import android.app.Activity;
// import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.

    private Button startGame;
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //no toolbar
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //fullscreen the display

        startGame = (Button)findViewById(R.id.start_game);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent game = new Intent(MainActivity.this, GameActivity.class);
                startActivity(game);
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
