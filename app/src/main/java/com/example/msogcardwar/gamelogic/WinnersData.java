package com.example.msogcardwar.gamelogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WinnersData {
    private final String[] scoreArr = new String[10];
    private final int[] iconsArr = new int[10];
    private String json;
    private Gson gson;
    private List<Winner> winners;
    private final Type winner_type = new TypeToken<List<Winner>>(){}.getType();

    //TODO:switch location to map location
    public void saveWinner(SharedPreferences mPrefs, int winner_score, int winner_img_id, Location location)  {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        boolean is_save = false;
        Winner myWinner = new Winner(winner_score, winner_img_id, location.getLatitude(), location.getLongitude());
        gson = new Gson();
        json = mPrefs.getString("Winners", "");
        winners = gson.fromJson(json, winner_type);


        if(winners == null){
            winners = new ArrayList<>();
            winners.add(myWinner);
            is_save = true;
        }
        else if (winners.size() == 10){
            int winner_index_to_remove = is_get_in_scoreboard(winners, myWinner);
            if(winner_index_to_remove != -1){
                winners.remove(winner_index_to_remove);
                winners.add(myWinner);
                is_save = true;
            }
        }
        else if (winners.size() < 10){
        //TODO: add winner to the list
            winners.add(myWinner);
            is_save = true;
        }

        if (is_save){
            winners.sort(Winner::compareTo);
            json = gson.toJson(winners);
            prefsEditor.putString("Winners", json);
            prefsEditor.apply();
        }
    }

    private int is_get_in_scoreboard(List<Winner> winners, Winner myWinner) {
        int i = 0;
        while( i != winners.size()){
            if (winners.get(i).getScore() <= myWinner.getScore()) return i;
        }
        return -1;
    }

    public CustomAdapter generateWinners(SharedPreferences mPrefs, Context applicationContext){
        gson = new Gson();
        json = mPrefs.getString("Winners", "");
        winners = gson.fromJson(json, winner_type);
        if (winners != null) {
            for (int i = 0; i < winners.size(); i++) {
                scoreArr[i] = "score: " + winners.get(i).getScore();
                iconsArr[i] = winners.get(i).getIcon();
            }
        }
        return new CustomAdapter(applicationContext, scoreArr, iconsArr);
    }

    public Winner getWinner(int i){
        if(winners.size() >= i){
            return winners.get(i);
        }
        return null;
    }
}
