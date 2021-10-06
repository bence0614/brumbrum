package com.example.brumbrum_tutorial;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.brumbrum_tutorial.gamepanel.GameOver;
import com.example.brumbrum_tutorial.gamepanel.Joystick;
import com.example.brumbrum_tutorial.gamepanel.Performance;
import com.example.brumbrum_tutorial.graphics.Sprite;
import com.example.brumbrum_tutorial.graphics.SpriteSheet;
import com.example.brumbrum_tutorial.map.Tilemap;
import com.example.brumbrum_tutorial.object.Circle;
import com.example.brumbrum_tutorial.object.Enemy;
import com.example.brumbrum_tutorial.object.Player;
import com.example.brumbrum_tutorial.object.Spell;

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
    //private final Enemy enemy;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;
    private GameOver gameOver;
    private Performance performance;
    private GameDisplay gameDisplay;
    private int startTime = 0;
    private MainActivity mainActivity;
    private Sprite sprite;

    public Game(Context context) {
        super(context);

        mainActivity = new MainActivity();


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
        Log.d("Game.java", "surface()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
        }
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

        performance.drawUPS(canvas);
        performance.drawFPS(canvas);
        performance.drawTime(canvas);


        joystick.draw(canvas);
        player.draw(canvas, gameDisplay);
        for (Enemy enemy : enemyList){
            enemy.draw(canvas, gameDisplay);
        }
        for (Spell spell : spellList){
            spell.draw(canvas, gameDisplay);
        }


        // Draw game over if the player is dead
        if(player.getHealthPoints() <= 0){
            gameOver.draw(canvas);
        }
    }

    public void update() {
        //stop updating the game if the player is dead
        if(player.getHealthPoints() <= 0){
            return;
        }

        joystick.update();
        player.update();

        if(Enemy.readyToSpawn()){
            SpriteSheet spriteSheet = new SpriteSheet(getContext());
            enemyList.add(new Enemy(getContext(), player, spriteSheet.getEnemySprite()));
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
                    break;
                }
            }
        }
        gameDisplay.update();
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}