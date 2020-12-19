package com.example.msogcardwar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msogcardwar.R;
import com.example.msogcardwar.gamelogic.WinnersData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class Winner_Activity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private TextView winner_lbl_winner_name;
    private ImageView winner_img_winner_logo;
    private TextView winner_lbl_winner_score;
    private int winner_score;
    private int winner_img_id;
    private Button start_game_btn;
    private Button score_board_btn;
    private MediaPlayer backgroundMusic;
    private SharedPreferences mPrefs;
    private FusedLocationProviderClient fusedLocationClient;
    public final WinnersData winnersData = new WinnersData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity);
        mPrefs = getSharedPreferences("myPrefs",MODE_PRIVATE);
        this.winner_score = getIntent().getIntExtra("winner_score", -1);
        this.winner_img_id = getIntent().getIntExtra("winner_image_id", -1);

        getPermissions();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
        getUserLastLocation();
    }

    private void openGameView() {
        Intent gameIntent= new Intent(Winner_Activity.this, Game_Activity.class);
        Winner_Activity.this.startActivity(gameIntent);
        backgroundMusic.release();
    }

    private void getPermissions(){
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)&&
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this,"We need permission please.", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    @SuppressLint("MissingPermission")
    private void getUserLastLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        winnersData.saveWinner(mPrefs, winner_score, winner_img_id, location);
                    }
                });
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
