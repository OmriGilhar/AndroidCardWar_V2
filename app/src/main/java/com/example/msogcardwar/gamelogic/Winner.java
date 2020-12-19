package com.example.msogcardwar.gamelogic;

public class Winner {
    private int score;
    private int icon;

    private double lat;
    private double lng;

    public Winner(int score, int icon, double lat, double lng) {
        this.setScore(score);
        this.setIcon(icon);
        this.setLat(lat);
        this.setLng(lng);
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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
