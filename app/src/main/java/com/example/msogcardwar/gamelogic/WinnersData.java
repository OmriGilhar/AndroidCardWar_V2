package com.example.msogcardwar.gamelogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class WinnersData {
    private final String[] scoreArr = new String[10];
    private final int[] iconsArr = new int[10];
    private String json;
    private Gson gson;
    private Winner[] winners;

    //TODO:switch location to map location
    public void saveWinner(SharedPreferences mPrefs, int winner_score, int winner_img_id, int location)  {
        Winner myWinner = new Winner(winner_score, winner_img_id, location);
        gson = new Gson();
        json = mPrefs.getString("Winners", "");
        winners = gson.fromJson(json, Winner[].class);

        if(winners == null){
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            winners = new Winner[10];
            winners[0] = myWinner;
            json = gson.toJson(winners);
            prefsEditor.putString("Winners", json);
            prefsEditor.apply();
        }
//        else if (winArray.length<10){
//      //TODO: add winner to the list
//        }
//        else if (winArray.length == 10){
//      //TODO: Search if there's a winner with a smaller score in the list. if so, replace.
//        }
    }


    public CustomAdapter generateWinners(SharedPreferences mPrefs, Context applicationContext){
        gson = new Gson();
        json = mPrefs.getString("Winners", "");
        winners = gson.fromJson(json, Winner[].class);

        if (winners != null) {
            for (int i = 0; i < winners.length; i++) {
                scoreArr[i] = "score: " + winners[i].getScore();
                Log.println(Log.DEBUG, "KAKAKA", scoreArr[i]);
                iconsArr[i] = winners[i].getIcon();
                Log.println(Log.DEBUG, "KAKAKA", iconsArr[i]+ "");
            }
        }
        return new CustomAdapter(applicationContext, scoreArr, iconsArr);
    }
}
