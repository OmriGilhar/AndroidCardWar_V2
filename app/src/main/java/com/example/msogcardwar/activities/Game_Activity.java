package com.example.msogcardwar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.msogcardwar.gamelogic.CardEntry;
import com.example.msogcardwar.gamelogic.Constants;
import com.example.msogcardwar.gamelogic.GameManager;
import com.example.msogcardwar.R;

import java.util.Stack;


public class Game_Activity extends AppCompatActivity {
    private TextView game_lbl_scorePlayer1;
    private ImageView game_img_card_player1;
    private TextView game_lbl_scorePlayer2;
    private ImageView game_img_card_player2;
    private ImageButton game_btn_play;
    private GameManager game_manager;
    private MediaPlayer backgroundMusic;
    private ProgressBar game_PB_Timer;
    private MyCountDownTimer myCountDownTimer;
    private Boolean isGameActive = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        findViews();
        initViews();

        if(savedInstanceState != null) {
            getScoresFromInstance(savedInstanceState);
            getStacksFromInstance(savedInstanceState);
            getPlayersCardFromInstance(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.PLAYER_ONE_STACK, game_manager.getPlayerOne().getPlayerStack());
        outState.putSerializable(Constants.PLAYER_TWO_STACK, game_manager.getPlayerTwo().getPlayerStack());

        // If someone spin the phone when no card as been played yet
        if(game_manager.getPlayerOne().getPlayerCard() != null){
            outState.putString(Constants.PLAYER_ONE_CURRENT_CARD_IMG, game_manager.getPlayerOne().getPlayerCard().getKey());
            outState.putInt(Constants.PLAYER_ONE_CURRENT_CARD_VALUE, game_manager.getPlayerOne().getPlayerCard().getValue());
            outState.putString(Constants.PLAYER_TWO_CURRENT_CARD_IMG, game_manager.getPlayerTwo().getPlayerCard().getKey());
            outState.putInt(Constants.PLAYER_TWO_CURRENT_CARD_VALUE, game_manager.getPlayerTwo().getPlayerCard().getValue());
        } else {
            outState.putString(Constants.PLAYER_ONE_CURRENT_CARD_IMG, getResources().getString(R.string.path_to_default_card));
            outState.putInt(Constants.PLAYER_ONE_CURRENT_CARD_VALUE, 0);
            outState.putString(Constants.PLAYER_TWO_CURRENT_CARD_IMG, getResources().getString(R.string.path_to_default_card));
            outState.putInt(Constants.PLAYER_TWO_CURRENT_CARD_VALUE, 0);
        }
        outState.putInt(Constants.PLAYER_ONE_SCORE, game_manager.getPlayerOne().getPlayerScore());
        outState.putInt(Constants.PLAYER_TWO_SCORE, game_manager.getPlayerTwo().getPlayerScore());
        outState.putInt(Constants.WINNER, game_manager.getWinner());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundMusic.release();
    }

    private void findViews() {
        game_lbl_scorePlayer1 = findViewById(R.id.game_LBL_scorePlayer1);
        game_img_card_player1 = findViewById(R.id.game_IMG_card_player1);
        game_lbl_scorePlayer2 = findViewById(R.id.game_LBL_scorePlayer2);
        game_img_card_player2 = findViewById(R.id.game_IMG_card_player2);
        game_btn_play = findViewById(R.id.game_BTN_play);
        game_PB_Timer = findViewById(R.id.game_PB_Timer);
    }

    private void initViews() {
        game_img_card_player1.setImageResource(R.drawable.card_back);
        game_img_card_player2.setImageResource(R.drawable.card_back);
        game_manager = new GameManager();
        refreshScoreView();
        game_manager.createGameStacks(false);

        backgroundMusic = MediaPlayer.create(this,
                R.raw.loyalty_freak_music10the_witch_are_going_magical);
        backgroundMusic.start();
        startGameTimer();

        game_btn_play.setOnClickListener(v -> nextRound());
    }


    private void startGameTimer(){
        myCountDownTimer = new MyCountDownTimer(5000, 1000);
        myCountDownTimer.start();
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished/50);
            Log.println(Log.DEBUG, "kaka", "----------");
            game_PB_Timer.setProgress(game_PB_Timer.getMax()-progress);
        }
        @Override
        public void onFinish() {
            if (isGameActive) nextRound();
        }
    }


    private void getPlayersCardFromInstance(Bundle savedInstanceState) {
        game_manager.getPlayerOne().setPlayerCard(new CardEntry<>(
                savedInstanceState.getString(Constants.PLAYER_ONE_CURRENT_CARD_IMG),
                savedInstanceState.getInt(Constants.PLAYER_ONE_CURRENT_CARD_VALUE)
        ));

        game_manager.getPlayerTwo().setPlayerCard(new CardEntry<>(
                savedInstanceState.getString(Constants.PLAYER_TWO_CURRENT_CARD_IMG),
                savedInstanceState.getInt(Constants.PLAYER_TWO_CURRENT_CARD_VALUE)
        ));

        refreshCardView(game_manager.getPlayerOne().getPlayerCard().getKey(), game_manager.getPlayerTwo().getPlayerCard().getKey());
    }

    private void getScoresFromInstance(Bundle savedInstanceState) {
        game_manager.getPlayerOne().setPlayerScore(savedInstanceState.getInt(Constants.PLAYER_ONE_SCORE));
        game_manager.getPlayerTwo().setPlayerScore(savedInstanceState.getInt(Constants.PLAYER_TWO_SCORE));
        game_manager.setWinner(savedInstanceState.getInt(Constants.WINNER));
        refreshScoreView();
    }

    @SuppressWarnings("unchecked")
    private void getStacksFromInstance(Bundle savedInstanceState) {
        try{
            game_manager.getPlayerOne().setPlayerStack((Stack<CardEntry<String,Integer>>)savedInstanceState.getSerializable(Constants.PLAYER_ONE_STACK));
            game_manager.getPlayerTwo().setPlayerStack((Stack<CardEntry<String,Integer>>)savedInstanceState.getSerializable(Constants.PLAYER_TWO_STACK));
        } catch (ClassCastException e){
            Log.println(Log.ERROR, "Casting", e.toString());
        }

    }

    private void nextRound() {
        int drawable_id;
        myCountDownTimer.cancel();
        game_manager.pullNewCards();
        game_manager.updateWinner();
        refreshCardView(game_manager.getPlayerOne().getPlayerCard().getKey(), game_manager.getPlayerTwo().getPlayerCard().getKey());
        refreshScoreView();

        switch(game_manager.getGameState()){
            case 1:
                drawable_id = this.getResources().getIdentifier("black_cat", "drawable", this.getPackageName());
                openWinnerView(game_manager.getPlayerOne().getPlayerScore(), drawable_id);
                break;
            case 2:
                drawable_id = this.getResources().getIdentifier("pirate", "drawable", this.getPackageName());
                openWinnerView(game_manager.getPlayerTwo().getPlayerScore(), drawable_id);
                break;
        }

        myCountDownTimer.start();
    }

    private void openWinnerView(int winner_score, int drawable_id) {
        isGameActive = false;
        Intent winnerView = new Intent(Game_Activity.this, Winner_Activity.class);
        winnerView.putExtra("winner_score", winner_score);
        winnerView.putExtra("winner_image_id", drawable_id);
        Game_Activity.this.startActivity(winnerView);
        backgroundMusic.release();
    }

    private void refreshCardView(String p1_card_image, String p2_card_image) {
        int player_one_drawable = this.getResources().getIdentifier(p1_card_image, "drawable", this.getPackageName());
        int player_two_drawable = this.getResources().getIdentifier(p2_card_image, "drawable", this.getPackageName());

        game_img_card_player1.setImageResource(player_one_drawable);
        game_img_card_player2.setImageResource(player_two_drawable);

    }

    private void refreshScoreView() {
        game_lbl_scorePlayer1.setText(String.valueOf(game_manager.getPlayerOne().getPlayerScore()));
        game_lbl_scorePlayer2.setText(String.valueOf(game_manager.getPlayerTwo().getPlayerScore()));
    }


}