package com.example.msogcardwar.gamelogic;


import android.location.Location;

public class Winner {
    private int score;
    private int icon;
    private Location location;

    public Winner(){}

    public Winner(int score, int icon, Location location) {
        this.setScore(score);
        this.setIcon(icon);
        this.setLocation(location);
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int compareTo(Winner winner2) {
        if(this.getScore() < winner2.getScore()){
            return 1;
        }
        else if(this.getScore() > winner2.getScore()){
            return -1;
        }
        return 0;
    }
}
