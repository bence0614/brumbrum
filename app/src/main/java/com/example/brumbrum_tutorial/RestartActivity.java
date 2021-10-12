package com.example.brumbrum_tutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.brumbrum_tutorial.gamepanel.Performance;

public class RestartActivity extends AppCompatActivity {


    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game(this);
        setContentView(R.layout.activity_restart);

        Button button = (Button) findViewById(R.id.textButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(game);
            }
        });
        //setContentView(game);

    }

    @Override
    protected void onStart() {
        super.onStart();

        int score = game.getEnemyKilled();
        TextView tv = (TextView) findViewById(R.id.score_text);
        tv.setText("Score :" + Integer.toString(score));


    }
    protected void onResume(){
        Log.d("RestartActivity.java","onResume");
        super.onResume();
    }
    protected void onPause(){
        Log.d("RestartActivity.java","onPause");
        game.pause();
        //setContentView(R.layout.activity_main);
        super.onPause();
    }
    protected void onStop(){
        Log.d("RestartActivity.java","onStop");
        //setContentView(R.layout.activity_main);
        super.onStop();
    }
    protected void onDestroy(){
        Log.d("RestartActivity.java","onDestroy");
        super.onDestroy();
    }

}