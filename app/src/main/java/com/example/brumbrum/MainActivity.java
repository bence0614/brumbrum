package com.example.brumbrum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.brumbrum.gamepanel.GameOver;
import com.example.brumbrum.sensor.Gyroscope;

/**
 * MainActivity is the entry point to our project
 */

public class MainActivity extends AppCompatActivity {

    private Game game;
    private Button scoreboard_button;
    private Gyroscope gyroscope;
    private GameOver gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity.java","onCreate");
        super.onCreate(savedInstanceState);

        // Set window to fullscreen(will hide status bar)
//        Window window = getWindow();
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//        );
        // Set content view to game, so that objects in the Game class can be rendered to the screen
        game = new Game(this);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.satartButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(game);
            }
        });
        scoreboard_button = findViewById(R.id.scoreboard_button);
        scoreboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatabaseMain.class);
                startActivity(intent);
            }
        });
        try{
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

          //gyroscope = new Gyroscope(this);
//        gyroscope.setListener(new Gyroscope.Listener() {
//            @Override
//            public void onRotation(float rx, float ry, float rz) {
//                if(rz > 1.0f){
//                    //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
//                    Log.d("MainActivity.java","rotation to Z");
//                }else if(rz < -1.0f){
//                    //getWindow().getDecorView().setBackgroundColor(Color.GREEN);
//                    Log.d("MainActivity.java","rotation to ----Z");
//                }
//            }
//        });
    }
    @Override
    protected void onStart(){
        Log.d("MainActivity.java","onStart");
        super.onStart();
    }
    protected void onResume(){
        Log.d("MainActivity.java","onResume");
        super.onResume();
        //gyroscope.register();
    }
    @Override
    protected void onPause(){
        Log.d("MainActivity.java","onPause");
        game.pause();

        //gyroscope.unregister();
        //setContentView(R.layout.activity_main);
        super.onPause();
    }
    @Override
    protected void onStop(){
        Log.d("MainActivity.java","onStop");
        //setContentView(R.layout.activity_main);
        super.onStop();
    }
    @Override
    protected void onDestroy(){
        Log.d("MainActivity.java","onDestroy");
        super.onDestroy();
    }
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}