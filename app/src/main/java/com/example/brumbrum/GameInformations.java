package com.example.brumbrum;

public class GameInformations {

    private int score;
    private double time;

    public GameInformations(double time, int score){
        this.time = time;
        this.score = score;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
