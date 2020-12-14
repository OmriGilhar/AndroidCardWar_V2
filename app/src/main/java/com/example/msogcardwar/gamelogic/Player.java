package com.example.msogcardwar.gamelogic;

import java.util.Stack;

public class Player {
    private Stack<CardEntry<String, Integer>> card_stack = new Stack<>();
    private int player_score = 0;
    private CardEntry<String, Integer> player_card;

    public CardEntry<String, Integer> getPlayerCard() {
        return player_card;
    }

    public Stack<CardEntry<String, Integer>> getPlayerStack(){
        return this.card_stack;
    }

    public int getCardValue(){
        return this.player_card.getValue();
    }

    public void addCardToStack(CardEntry<String, Integer> card){
        card_stack.add(card);
    }

    public void increasePlayerScore(){
        this.player_score++;
    }

    public void nextCard(){
        player_card = card_stack.pop();
    }

    public int getPlayerScore(){
        return this.player_score;
    }

    public void setPlayerCard(CardEntry<String, Integer> card) {
        this.player_card = card;
    }

    public void setPlayerScore(int score) {
        this.player_score = score;
    }

    public void setPlayerStack(Stack<CardEntry<String, Integer>> playerStack) {
        this.card_stack = playerStack;
    }
}
