package com.example.brumbrum_tutorial.gamepanel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.brumbrum_tutorial.GameLoop;
import com.example.brumbrum_tutorial.MainActivity;
import com.example.brumbrum_tutorial.R;
import com.example.brumbrum_tutorial.RestartActivity;

/**
 * GameOver is  panel that draws "game over" text to the screen
 */

public class GameOver{

    private Context context;
    private int score;

    public GameOver(Context context){
        this.context = context;
    }

    public void draw(Canvas canvas){
        String text = "Game Over";

        float x = 800;
        float y = 200;

        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(color);
        float textSize = 150;
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);


    }
    public void gameOver(int score){
        this.score = score;

        Intent intent = new Intent(context, RestartActivity.class);
        context.startActivity(intent);
    }

    public int getScore() {
        return score;
    }
}
