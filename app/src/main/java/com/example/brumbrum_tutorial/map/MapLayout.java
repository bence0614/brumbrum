package com.example.brumbrum_tutorial.map;

import java.util.Random;

public class MapLayout {
    public static final int TILE_WIDTH_PIXELS = 64;
    public static final int TILE_HEIGHT_PIXELS = 64;
    public static final int NUMBER_OF_ROW_TILES = 60;
    public static final int NUMBER_OF_COLUMN_TILES = 60;

    private int[][] layout;

    public MapLayout(){
        initializeLayout();
    }

    private void initializeLayout() {
        layout = new int[NUMBER_OF_COLUMN_TILES][NUMBER_OF_ROW_TILES];
        Random rnd = new Random();

        for (int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMN_TILES; j++) {
                //layout[i][j] = rnd.nextInt(2);
                layout[i][j] = 1;
                System.out.println(layout[i][j]);
            }
        }
    }

    public int[][] getLayout(){
        return layout;
    }
}
