package com.example.msogcardwar.gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public class GameManager {
    private int winner;
    private final ArrayList<CardEntry<String, Integer>> all_card_stack = new ArrayList<>();
    private final Player player_one = new Player();
    private final Player player_two = new Player();

    public Player getPlayerOne() {
        return player_one;
    }

    public Player getPlayerTwo() {
        return player_two;
    }

    public GameManager(){
        this.winner = 0;
    }

    public int getWinner() {
        return this.winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void pullNewCards(){
        player_one.nextCard();
        player_two.nextCard();
    }

    public int getGameState() {
        if (player_one.getPlayerStack().isEmpty() || player_two.getPlayerStack().isEmpty())
        {
            if (player_one.getPlayerScore() == player_two.getPlayerScore()){
                createGameStacks(true);
                return 0;
            } else {
                return this.winner;
            }

        }
        return -1;
    }


    public void updateWinner() {
        if(player_one.getCardValue() > player_two.getCardValue()){
            player_one.increasePlayerScore();
        }else if(player_two.getCardValue() > player_one.getCardValue()){
            player_two.increasePlayerScore();
        }

        if (player_one.getPlayerScore() > player_two.getPlayerScore()){
            this.winner = 1;
        } else if(player_two.getPlayerScore() > player_one.getPlayerScore()){
            this.winner = 2;
        } else {
            this.winner = 0;
        }
    }


    public void createGameStacks(boolean isTie) {
        int card_value;
        int card_shape;
        int i=0;

        /*
        Fill overall stack with all the possible cards.
        with the picture template:

            poker_card_<card shape>_<card_value>

         */
        for(card_shape=1; card_shape<5; card_shape++)
        {
            for(card_value=2; card_value<15; card_value++){
                String card_key = "poker_card_" + CardShapesEnum.valueOf(card_shape).toString().toLowerCase() + "_" + card_value;
                CardEntry<String, Integer> card_entry = new CardEntry<>(card_key, card_value);
                all_card_stack.add(card_entry);
            }
        }

        Collections.shuffle(all_card_stack);

        /*
         * Divide the cards between the players.
         * We divide the cards equally between the players (even amount of cards) fo each time we
         * add a card to a player we remove the card from the overall stack.
         * In case of tie, give only 5 cards.
         * */
        while(all_card_stack.size() != 0 && i < 5){
            // Add card to player one stack and remove it from overall stack
            player_one.addCardToStack(all_card_stack.get(0));
            all_card_stack.remove(0);

            // Add card to player two stack and remove it from overall stack
            player_two.addCardToStack(all_card_stack.get(0));
            all_card_stack.remove(0);

            if(isTie)
                i++;

        }
    }


}





