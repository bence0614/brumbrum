package com.example.brumbrum_tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RestartActivity extends AppCompatActivity {

    Button scoreboard_button;
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

        scoreboard_button = findViewById(R.id.scoreboard_button);
        scoreboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestartActivity.this, DatabaseMain.class);
                startActivity(intent);
            }
        });
        //setContentView(game);

    }

    @Override
    protected void onStart() {
        super.onStart();
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