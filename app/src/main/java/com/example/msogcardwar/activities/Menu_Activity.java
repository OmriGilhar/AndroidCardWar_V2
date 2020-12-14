package com.example.msogcardwar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import com.example.msogcardwar.R;

public class Menu_Activity extends AppCompatActivity {

    private Button start_game_btn;
    private Button scoreboard_btn;
    private MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        find_views();
        init_views();
    }

    private void find_views() {
        start_game_btn = findViewById(R.id.startGame_BTN_menu);
        scoreboard_btn = findViewById(R.id.scoreboard_BTN_menu);
    }

    private void init_views() {
        start_game_btn.setOnClickListener(v -> openGameView());
        scoreboard_btn.setOnClickListener(v -> openScoreboardView());
        backgroundMusic = MediaPlayer.create(this, R.raw.loyalty_freak_music04hello_regan);
        backgroundMusic.start();
    }


    private void openScoreboardView() {
        Intent gameIntent= new Intent(Menu_Activity.this, Scoreboard_Activity.class);
        Menu_Activity.this.startActivity(gameIntent);
        backgroundMusic.release();
    }

    private void openGameView() {
        Intent gameIntent= new Intent(Menu_Activity.this, Game_Activity.class);
        Menu_Activity.this.startActivity(gameIntent);
        backgroundMusic.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundMusic.release();
    }
}