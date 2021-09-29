package com.example.brumbrum_tutorial.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.brumbrum_tutorial.GameLoop;
import com.example.brumbrum_tutorial.R;

/**
 * Enemy that follows the player
 * The Enemy class is an extension of a Circle, which is extension of a GameObject
 */
public class Enemy extends Circle{
    private  static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND*0.6;
    private  static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updateUntilNextSpawn = UPDATES_PER_SPAWN;

    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius){
        super(ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);
        
        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(ContextCompat.getColor(context, R.color.enemy),
                Math.random()*1000,
                Math.random()*1000,
                30
        );
        this.player = player;
    }

    /**
     * is enemy is ready to spawn
     * decided max number per minute (see SPAWN_PER_MINUTE at top)
     * @return
     */

    public static boolean readyToSpawn() {
        if (updateUntilNextSpawn <= 0){
            updateUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }else {
            updateUntilNextSpawn --;
            return false;
        }
    }

    @Override
    public void update() {
        //update the velocity of the enemy  so that the velocity is in the direction of the player

        //calculate vector from enemy to player (in x and y)
        double distanceToPlayerX = player.getPositionX() - positionX ;
        double distanceToPlayerY = player.getPositionY() - positionY ;

        //calc absolute distance between enemy and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        //calc direction from enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        //set velocity in the direction to the player
        if(distanceToPlayer > 0){ // no divide by zero
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        }else{
            velocityX = 0;
            velocityY = 0;
        }

        //update the position of the enemy
        positionX += velocityX;
        positionY += velocityY;
    }
}
