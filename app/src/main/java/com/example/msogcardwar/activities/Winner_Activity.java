package com.example.msogcardwar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msogcardwar.R;
import com.example.msogcardwar.gamelogic.WinnersData;


public class Winner_Activity extends AppCompatActivity {

    private TextView winner_lbl_winner_name;
    private ImageView winner_img_winner_logo;
    private TextView winner_lbl_winner_score;
    private int winner_score;
    private int winner_img_id;
    private Button start_game_btn;
    private Button score_board_btn;
    private final WinnersData winnersData = new WinnersData();
    private MediaPlayer backgroundMusic;
    private Location location;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity);
        mPrefs = getSharedPreferences("myPrefs",MODE_PRIVATE);
        this.winner_score = getIntent().getIntExtra("winner_score", -1);
        this.winner_img_id = getIntent().getIntExtra("winner_image_id", -1);
        this.location = (Location) getIntent().getSerializableExtra("winner_location");

        findViews();
        initViews();
    }

    private void findViews() {
        winner_lbl_winner_name = findViewById(R.id.winner_LBL_winner_title);
        winner_img_winner_logo = findViewById(R.id.winner_IMG_winner);
        winner_lbl_winner_score = findViewById(R.id.winner_LBL_description);
        start_game_btn = findViewById(R.id.winner_BTN_startNewGame);
        score_board_btn = findViewById(R.id.winner_BTN_score_board);
    }

    private void initViews() {
        String winner_score_title = "Your Score is :" + this.winner_score;
        winner_lbl_winner_name.setText(R.string.winner_title);
        winner_lbl_winner_score.setText(winner_score_title);
        winner_img_winner_logo.setImageResource(winner_img_id);
        start_game_btn.setOnClickListener(v -> openGameView());
        score_board_btn.setOnClickListener(v -> openScoreboardView());
        backgroundMusic = MediaPlayer.create(this, R.raw.loyalty_freak_music04hello_regan);
        backgroundMusic.start();
        winnersData.saveWinner(mPrefs, this.winner_score, winner_img_id, location);
    }

    private void openGameView() {
        Intent gameIntent= new Intent(Winner_Activity.this, Game_Activity.class);
        Winner_Activity.this.startActivity(gameIntent);
        backgroundMusic.release();
    }

    private void openScoreboardView() {
        Intent gameIntent= new Intent(Winner_Activity.this, Scoreboard_Activity.class);
        Winner_Activity.this.startActivity(gameIntent);
        backgroundMusic.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundMusic.release();
    }
}
