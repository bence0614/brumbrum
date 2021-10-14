package com.example.brumbrum;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;

import com.example.brumbrum.database.DatabaseHelper;
import com.example.brumbrum.gamepanel.Joystick;
import com.example.brumbrum.gamepanel.Performance;
import com.example.brumbrum.graphics.SpriteSheet;
import com.example.brumbrum.map.Tilemap;
import com.example.brumbrum.object.Circle;
import com.example.brumbrum.object.Enemy;
import com.example.brumbrum.object.Player;
import com.example.brumbrum.object.Spell;
import com.example.brumbrum.sensor.Gyroscope;

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
    private final Tilemap tilemap;

    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;

    private GameLoop gameLoop;
    private GameOver gameOver;
    private Performance performance;
    private GameDisplay gameDisplay;
    private DatabaseHelper databaseHelper;
    private Gyroscope gyroscope;

    private int enemyKilled = 0;
    private double enemySpeed = 1;
    private double oldSpeed = 1;
    private double scoreMultipler = 1;
    private int finalScore;

    private boolean first = true;

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //initialize game panels
        performance = new Performance(context, gameLoop);
        gameOver = new GameOver(getContext());
        joystick = new Joystick(275,735,100,50);

        //INITIALIZE game objects
        SpriteSheet spriteSheet = new SpriteSheet(context);
        player = new Player(context, joystick,2*500,500, 32*3, spriteSheet.getPlayerSprite());

        //Initialize game display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

        //Initialize tilemap
        tilemap = new Tilemap(spriteSheet);

        //Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(context);

        //Detecting if the user's hand is shaking with gyroscope sensor
        //Increasing the enemies speed if rotation detected
        gyroscope = new Gyroscope(context);
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if(ry > 0.3f){
                    Log.d("Game","rotation to RY");
                    enemySpeed +=0.1d;
                }else if(ry < -0.3f){
                    Log.d("Game","rotation to -RY");
                    enemySpeed +=0.1d;
                }else if(rx < -0.3f){
                    Log.d("Game","rotation to RX");
                    enemySpeed +=0.1d;
                }else if(rx < -0.3f){
                    Log.d("Game","rotation to -RX");
                    enemySpeed +=0.1d;
                }
            }
        });

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Handling multiple tuch event from the user
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
        Log.d("Game.java", "surface()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
        }
        //register gyroscope
        gyroscope.register();
        //start the gameloop
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Draw tilemap
        tilemap.draw(canvas, gameDisplay);

        //Draw performance details
        performance.drawUPS(canvas);
        performance.drawFPS(canvas);
        performance.drawTime(canvas);
        performance.drawMultipler(canvas, (int)scoreMultipler);
        performance.drawScore(canvas, enemyKilled);

        //Drawing gameObjects
        joystick.draw(canvas);
        player.draw(canvas, gameDisplay);
        for (Enemy enemy : enemyList){
            enemy.draw(canvas, gameDisplay);
        }
        for (Spell spell : spellList){
            spell.draw(canvas, gameDisplay);
        }
    }

    public void update() {

        //stop updating the game if the player is dead
        if(player.getHealthPoints() <= 0){
            double time = gameLoop.getTotalTime();

            if(first){
                first = false;
                databaseHelper.addScore(enemyKilled, time);
            }

            gameOver.gameOver(enemyKilled);
            return;
        }

        //Update gameObjects
        joystick.update();
        player.update();

        if(Enemy.readyToSpawn()){
            SpriteSheet spriteSheet = new SpriteSheet(getContext());
            enemyList.add(new Enemy(getContext(), player, spriteSheet.getEnemySprite(), enemySpeed));
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
                player.setHealthPoints(player.getHealthPoints() -1);
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
                    if(enemySpeed - oldSpeed > 0.3){
                        oldSpeed = enemySpeed;
                        scoreMultipler++;
                    }
                    enemyKilled+= 1*scoreMultipler;
                    break;
                }
            }
        }
        gameDisplay.update();
    }

    public void pause() {
        //Unregister gyroscope
        gyroscope.unregister();
        //stop gameLoop
        gameLoop.stopLoop();

    }
}