package com.example.brumbrum_tutorial.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.brumbrum_tutorial.GameLoop;
import com.example.brumbrum_tutorial.Joystick;
import com.example.brumbrum_tutorial.R;

/**
 * Player - the main character controlled by joystick
 * The class is an extension of the Circle, which is an extension of the GameObject
 */
public class Player extends Circle{
    public   static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private  static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private Joystick joystick;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);

        this.joystick = joystick;

/*
        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
*/
    }

    @Override
    public void update() {
        //update velocity based on the actuator of the joystick
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;

        //update position
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
