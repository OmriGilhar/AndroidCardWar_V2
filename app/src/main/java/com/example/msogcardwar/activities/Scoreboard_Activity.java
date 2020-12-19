package com.example.msogcardwar.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.msogcardwar.R;
import com.example.msogcardwar.gamelogic.CustomAdapter;

import com.example.msogcardwar.gamelogic.Winner;
import com.example.msogcardwar.gamelogic.WinnersData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ListView;

public class Scoreboard_Activity extends AppCompatActivity implements OnMapReadyCallback{

    private Button menu_btn;
    private final WinnersData winnersData = new WinnersData();
    private ListView simple_list;
    private CustomAdapter custom_adapter;
    private GoogleMap google_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorebaord_activity);
        SharedPreferences mPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        custom_adapter = winnersData.generateWinners(mPrefs, getApplicationContext());
        find_views();
        initViews();
    }

    private void find_views() {
        simple_list = findViewById(R.id.winner_ListView);
        menu_btn = findViewById(R.id.sb_BTN_goToMenu);
    }

    private void initViews() {
        simple_list.setAdapter(custom_adapter);
        menu_btn.setOnClickListener(v -> openMenuView());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_MAP_small);

        mapFragment.getMapAsync(this);

        simple_list.setOnItemClickListener((a, v, position, id) -> {
            Winner winner = winnersData.getWinner(position);
            LatLng winner_location = new LatLng(winner.getLat(), winner.getLng());
            google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(winner_location, 15));
            google_map.addMarker(new MarkerOptions().position(winner_location).title("Your Score: "+winner.getScore()));
        });
    }

    private void openMenuView() {
        Intent gameIntent= new Intent(Scoreboard_Activity.this, Menu_Activity.class);
        Scoreboard_Activity.this.startActivity(gameIntent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        google_map = googleMap;
    }
}