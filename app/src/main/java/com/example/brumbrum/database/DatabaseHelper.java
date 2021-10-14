package com.example.brumbrum.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Creates the database
 * Stores the function needed to access and modify the database
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ScoreBoard.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "ScoreBoard";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SCORE = "player_score";
    private static final String COLUMN_TIME = "player_time";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SCORE + " INTEGER, " +
                COLUMN_TIME + " DOUBLE);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public void addScore(int score, double time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SCORE, score);
        cv.put(COLUMN_TIME, time);
        db.insert(TABLE_NAME,null, cv);
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public int getLatIndex(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX("+COLUMN_ID+") FROM "+TABLE_NAME, null);
        int maxid = (cursor.moveToFirst() ? cursor.getInt(0) : 0);

        return maxid;
    }

    public int getBestScore(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX("+COLUMN_SCORE+") FROM "+TABLE_NAME, null);
        int maxScore = (cursor.moveToFirst() ? cursor.getInt(0) : 0);

        return maxScore;
    }

    public double[] getLastScore(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT player_score, player_time FROM "+TABLE_NAME+ " WHERE _id = "+getLatIndex(), null);
        cursor.moveToFirst();

        double[] lastScore = new double[2];
        lastScore[0] = (double) cursor.getInt(0);
        lastScore[1] = cursor.getDouble(1);

        return lastScore;
    }
}
