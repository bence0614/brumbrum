package com.example.brumbrum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.brumbrum.MainActivity;
import com.example.brumbrum.R;
import com.example.brumbrum.RestartActivity;

/**
 * GameOver is a class handling game over and starts the restart activity
 */

public class GameOver{

    private Context context;
    private int score;

    public GameOver(Context context){
        this.context = context;
    }

    public void gameOver(int score){
        this.score = score;

        Intent intent = new Intent(context, RestartActivity.class);
        context.startActivity(intent);
    }
}
