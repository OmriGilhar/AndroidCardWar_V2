package com.example.msogcardwar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.msogcardwar.R;
import com.example.msogcardwar.gamelogic.CustomAdapter;

import com.example.msogcardwar.gamelogic.WinnersData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class Scoreboard_Activity extends AppCompatActivity {

    private RelativeLayout sb_LAY_sb_list;
    private FrameLayout sb_LAY_location;
    private ListView sb_LAY_sb_listw;
    private View google_MAP_small;
    private Button start_game_btn;
    private final WinnersData winnersData = new WinnersData();
    private ListView simpleList;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorebaord_activity);
        SharedPreferences mPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        simpleList = findViewById(R.id.winner_ListView);
        customAdapter = winnersData.generateWinners(mPrefs, getApplicationContext());
        find_views();
        initViews();
    }

    private void find_views() {
        sb_LAY_sb_list = findViewById(R.id.sb_LAY_sb_list);
        sb_LAY_location = findViewById(R.id.sb_LAY_location);
        sb_LAY_sb_listw = findViewById(R.id.winner_ListView);
        google_MAP_small = findViewById(R.id.google_MAP_small);
        start_game_btn = findViewById(R.id.sb_BTN_goToMenu);

    }

    private void initViews() {
        simpleList.setAdapter(customAdapter);
        start_game_btn.setOnClickListener(v -> openMenuView());
        Fragment mapFr = new Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.sb_LAY_location, mapFr).commit();
    }

    private void openMenuView() {
        Intent gameIntent= new Intent(Scoreboard_Activity.this, Menu_Activity.class);
        Scoreboard_Activity.this.startActivity(gameIntent);
    }

}