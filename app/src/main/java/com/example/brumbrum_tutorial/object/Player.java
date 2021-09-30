package com.example.brumbrum_tutorial.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.brumbrum_tutorial.GameLoop;
import com.example.brumbrum_tutorial.gamepanel.Joystick;
import com.example.brumbrum_tutorial.R;
import com.example.brumbrum_tutorial.Utils;
import com.example.brumbrum_tutorial.gamepanel.HealthBar;

/**
 * Player - the main character controlled by joystick
 * The class is an extension of the Circle, which is an extension of the GameObject
 */
public class Player extends Circle{
    public   static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final int MAX_HEALTH_POINTS = 10;
    private  static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);

        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
        this.healthPoints = MAX_HEALTH_POINTS;

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
    public void draw(Canvas canvas) {
        super.draw(canvas);
        healthBar.draw(canvas);
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