package com.example.brumbrum.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.brumbrum.graphics.Sprite;
import com.example.brumbrum.graphics.SpriteSheet;

public class GroundTile extends Tile {
    private final Sprite sprite;

    public GroundTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getGroundSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.drawMap(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
