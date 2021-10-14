package com.example.brumbrum.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.brumbrum.GameLoop;
import com.example.brumbrum.R;

public class Performance {
    private GameLoop gameLoop;
    private Context context;
    private double startTime = 0;

    public Performance(Context context, GameLoop gameLoop){
        this.context = context;
        this.gameLoop = gameLoop;
    }

    public void draw(Canvas canvas){
        drawUPS(canvas);
        drawFPS(canvas);
    }
    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 50, paint);
    }

    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 100, paint);
    }

    public void drawTime(Canvas canvas){
        String time = Double.toString(gameLoop.getTotalTime());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("TIME " + time, canvas.getWidth()-400, 50, paint);
    }
    public void drawScore(Canvas canvas, int score){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("SCORE: " + score, (int)(canvas.getWidth()/2), 100, paint);
    }
    public void drawMultipler(Canvas canvas, int multipler){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("Multipler " + multipler, (int)(canvas.getWidth()/2), 50, paint);
    }
}
