package com.example.brumbrum_tutorial.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.brumbrum_tutorial.R;

public class SpriteSheet {
    private Bitmap bitmap;

    public SpriteSheet(Context context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cowboy_head, bitmapOptions);
    }

    public Sprite getPlayerSprite(){
        return new Sprite(this, new Rect(0,0,200,200));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
