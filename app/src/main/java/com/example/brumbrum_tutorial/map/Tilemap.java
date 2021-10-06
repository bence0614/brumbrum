package com.example.brumbrum_tutorial.map;

import static com.example.brumbrum_tutorial.map.MapLayout.NUMBER_OF_COLUMN_TILES;
import static com.example.brumbrum_tutorial.map.MapLayout.NUMBER_OF_ROW_TILES;
import static com.example.brumbrum_tutorial.map.MapLayout.TILE_HEIGHT_PIXELS;
import static com.example.brumbrum_tutorial.map.MapLayout.TILE_WIDTH_PIXELS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.brumbrum_tutorial.GameDisplay;
import com.example.brumbrum_tutorial.graphics.SpriteSheet;

public class Tilemap {

    private final MapLayout mapLayout;
    private Tile[][] tilemap;
    private SpriteSheet spriteSheet;
    private Bitmap mapBitmap;

    public Tilemap(SpriteSheet spriteSheet){
        mapLayout = new MapLayout();
        this.spriteSheet = spriteSheet;

         initializeTilemap();
    }

    private void initializeTilemap() {
        int[][] layout = mapLayout.getLayout();
        tilemap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COLUMN_TILES];
        for (int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMN_TILES; j++) {
                tilemap[i][j] = Tile.getTile(
                        layout[i][j],
                        spriteSheet,
                        getRectByIndex(i, j)
                );
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
                NUMBER_OF_COLUMN_TILES*TILE_WIDTH_PIXELS,
                NUMBER_OF_ROW_TILES*TILE_HEIGHT_PIXELS,
                config
        );

        Canvas mapCanvas = new Canvas(mapBitmap);

        for (int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMN_TILES; j++) {
                tilemap[i][j].draw(mapCanvas);
            }
        }
    }

    private Rect getRectByIndex(int idxRow, int idxCol){
        return new Rect(
                idxCol * TILE_WIDTH_PIXELS,
                idxRow * TILE_HEIGHT_PIXELS,
                (idxCol + 1) * TILE_WIDTH_PIXELS,
                (idxCol + 1) * TILE_HEIGHT_PIXELS
        );
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawBitmap(
                mapBitmap,
                gameDisplay.getGameRect(),
                gameDisplay.DISPLAY_RECT,
                null
        );
    }
}
