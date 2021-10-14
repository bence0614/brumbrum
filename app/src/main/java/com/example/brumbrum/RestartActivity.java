package com.example.brumbrum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.brumbrum.database.DatabaseHelper;

/**
 * The activity shown on game over
 * Displaying the score of the game
 * Providing the opportuniti to start a new game by pressing restart
 */
public class RestartActivity extends AppCompatActivity {

    private Button scoreboard_button;
    private Game game;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game(this);
        databaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_restart);

        //Initializing the TextViews
        TextView score_value, time_score_value_text, best_score_value_text;

        score_value = (TextView)findViewById(R.id.score_value);
        score_value.setText(Integer.toString((int)databaseHelper.getLastScore()[0]));

        time_score_value_text = (TextView)findViewById(R.id.time_score_value_text);
        time_score_value_text.setText(Double.toString(databaseHelper.getLastScore()[1]));

        best_score_value_text = (TextView)findViewById(R.id.score_best_value_text);
        best_score_value_text.setText(Integer.toString(databaseHelper.getBestScore()));

        //Initializing the buttons
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
                Intent intent = new Intent(RestartActivity.this, DatabaseMain.class);
                startActivity(intent);
            }
        });
    }

    //Override methods
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume(){
        Log.d("RestartActivity.java","onResume");
        super.onResume();
    }
    @Override
    protected void onPause(){
        Log.d("RestartActivity.java","onPause");
        game.pause();
        super.onPause();
    }
    @Override
    protected void onStop(){
        Log.d("RestartActivity.java","onStop");
        super.onStop();
    }
    @Override
    protected void onDestroy(){
        Log.d("RestartActivity.java","onDestroy");
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