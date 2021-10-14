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

import com.example.brumbrum.sensor.Gyroscope;

/**
 * MainActivity is the entry point to the project
 */

public class MainActivity extends AppCompatActivity {

    private Game game;
    private Button scoreboard_button, start_button;
    private Gyroscope gyroscope;
    private GameOver gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity.java","onCreate");
        super.onCreate(savedInstanceState);

        game = new Game(this);
        setContentView(R.layout.activity_main);

        //Initializing activity_main buttons
        start_button = (Button) findViewById(R.id.satartButton);
        start_button.setOnClickListener(new View.OnClickListener() {
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

        //Vibrate when the application starts
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
    @Override
    protected void onPause(){
        Log.d("MainActivity.java","onPause");
        game.pause();
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