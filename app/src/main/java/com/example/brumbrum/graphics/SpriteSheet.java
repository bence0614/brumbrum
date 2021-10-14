package com.example.brumbrum.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.brumbrum.R;

public class SpriteSheet {
    private static final int SPRITE_WIDTH_PIXELS = 64;
    private static final int SPRITE_HEIGHT_PIXELS = 64;
    private Bitmap bitmap;

    public SpriteSheet(Context context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
    }

    public Sprite getPlayerSprite(){
        return new Sprite(this, new Rect(0,0,64,64));
    }
    public Sprite getEnemySprite(){
        return new Sprite(this, new Rect(64,0,64*2,64));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Sprite getGroundSprite() {
        return getSpriteByIndex(1,1);
    }
    public Sprite getGrassSprite() {
        return getSpriteByIndex(0,1);
    }

    private Sprite getSpriteByIndex(int idxRow, int idxCol) {
        return new Sprite(this, new Rect(
                idxCol * SPRITE_WIDTH_PIXELS,
                idxRow * SPRITE_HEIGHT_PIXELS,
                (idxCol + 1) * SPRITE_WIDTH_PIXELS,
                (idxCol + 1) * SPRITE_HEIGHT_PIXELS
        ));
    }
}
