package com.example.brumbrum_tutorial.object;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Circle is an abstract class which implements a draw method from GameObject for
 * drawing com.example.brumbrum_tutorial.object as a circle
 */

public abstract class Circle extends GameObject{

    protected double radius;
    protected Paint paint;

    public Circle(int color, double positionX, double positionY, double radius){
        super(positionX, positionY);

        this.radius = radius;

        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * isColliding checks if two circle obj is colliding based on the position and radius
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceCollision = obj1.getRadius() + obj2.getRadius();

        if (distance < distanceCollision){
            return true;
        }else {
            return false;
        }
    }

    private double getRadius(){
        return radius;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }
}
