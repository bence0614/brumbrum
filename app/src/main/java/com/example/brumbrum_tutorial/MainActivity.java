package com.example.brumbrum_tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

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
        setContentView(game);
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
        super.onPause();
    }
    protected void onStop(){
        Log.d("MainActivity.java","onStop");
        super.onStop();
    }
    protected void onDestroy(){
        Log.d("MainActivity.java","onDestroy");
        super.onDestroy();
    }

}