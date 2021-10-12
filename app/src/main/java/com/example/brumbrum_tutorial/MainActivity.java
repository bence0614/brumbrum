package com.example.brumbrum_tutorial;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * MainActivity is the entry point to our project
 */

public class MainActivity extends AppCompatActivity {

    private Game game;

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

        Button button = (Button) findViewById(R.id.textButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(game);
            }
        });
        //setContentView(game);
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
    }
    @Override
    protected void onStart(){
        Log.d("MainActivity.java","onStart");
        super.onStart();
    }
    protected void onResume(){
        Log.d("MainActivity.java","onResume");
        super.onResume();
    }
    protected void onPause(){
        Log.d("MainActivity.java","onPause");
        game.pause();
        //setContentView(R.layout.activity_main);
        super.onPause();
    }
    protected void onStop(){
        Log.d("MainActivity.java","onStop");
        //setContentView(R.layout.activity_main);
        super.onStop();
    }
    protected void onDestroy(){
        Log.d("MainActivity.java","onDestroy");
        super.onDestroy();
    }
}