package com.example.brumbrum_tutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.brumbrum_tutorial.object.Circle;
import com.example.brumbrum_tutorial.object.Enemy;
import com.example.brumbrum_tutorial.object.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Game manages all objects in the game and is responsible for updating all states
 * and render all objects on the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Player player;
    private final Joystick joystick;
    //private final Enemy enemy;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //INITIALIZE game objects
        joystick = new Joystick(275,350,100,50);
        player = new Player(getContext(), joystick,2*500,500, 50);
        //enemy = new Enemy(getContext(), player,2*500,200, 80);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()){
                    //if joystick was pressed before this event -> cast spell
                    numberOfSpellsToCast ++;

                }else if(joystick.isPressed((double)event.getX(),(double)event.getY())){
                    //joystick is pressed in this event -> setIsPressed(true)
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                }else {
                    //joystick was not previously and is not pressed in this event -> cast spell
                    spellList.add(new Spell(getContext(), player));
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                //joystick was pressed before and is now moved
                if(joystick.getIsPressed()){
                    joystick.setActuator((double)event.getX(),(double)event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId((event.getActionIndex()))){
                    //joystick was let go of -> setIsPressed(false) and resetActuator
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 50, paint);
    }

    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 100, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);
        for (Enemy enemy : enemyList){
            enemy.draw(canvas);
        }
        for (Spell spell : spellList){
            spell.draw(canvas);
        }
    }

    public void update() {
        joystick.update();
        player.update();
        //enemy.update();

        if(Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(), player));
        }

        //update state of each enemy
        while (numberOfSpellsToCast > 0){
            spellList.add(new Spell(getContext(), player));
            numberOfSpellsToCast --;
        }
        for (Enemy enemy: enemyList){
            enemy.update();
        }

        //update state of each spell
        for (Spell spell: spellList){
            spell.update();
        }

        //Iterate through enemyList and check for collision between enemy and player
        Iterator<Enemy> enemyIterator = enemyList.iterator();
        while (enemyIterator.hasNext()){
            Circle enemy = enemyIterator.next();
            if(Circle.isColliding(enemy, player)){
                //Remove enemy if it collides with the player
                enemyIterator.remove();
                continue;
            }
            //Iterate through spellList and check for collision between spell and enemy
            Iterator<Spell> spellIterator = spellList.iterator();
            while (spellIterator.hasNext()){
                Circle spell = spellIterator.next();
                if(Circle.isColliding(spell, enemy)){
                    //Remove enemy if it collides with the enemy
                    spellIterator.remove();
                    enemyIterator.remove();
                    break;
                }
            }
        }
    }
}