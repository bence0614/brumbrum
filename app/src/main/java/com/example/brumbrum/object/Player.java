package com.example.brumbrum.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.brumbrum.GameDisplay;
import com.example.brumbrum.GameLoop;
import com.example.brumbrum.gamepanel.Joystick;
import com.example.brumbrum.R;
import com.example.brumbrum.Utils;
import com.example.brumbrum.gamepanel.HealthBar;
import com.example.brumbrum.graphics.Sprite;

/**
 * Player - the main character controlled by joystick
 * The class is an extension of the Circle, which is an extension of the GameObject
 */
public class Player extends Circle{
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final int MAX_HEALTH_POINTS = 5;
    private  static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;
    private Sprite sprite;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius, Sprite sprite) {
        super(ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);

        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
        this.healthPoints = MAX_HEALTH_POINTS;
        this.sprite = sprite;

    }

    @Override
    public void update() {
        //update velocity based on the actuator of the joystick
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;

        //update position
        positionX += velocityX;
        positionY += velocityY;

        //update direction
        if(velocityX != 0 ||velocityY != 0){
            //normalize velocity to get direction(unit vector of velocity
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {

        sprite.draw(canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(getPositionX()) - 32,
                (int) gameDisplay.gameToDisplayCoordinatesY(getPositionY()) - 32
        );
//        super.draw(canvas, gameDisplay);
        healthBar.draw(canvas, gameDisplay);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if(healthPoints >= 0){
            this.healthPoints = healthPoints;
        }
    }
}