package com.example.msogcardwar.gamelogic;
import java.util.HashMap;

public class CardEntry<String, integer> implements HashMap.Entry<String, integer> {
    private final String key;
    private integer value;

    public CardEntry(String key, integer value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public integer getValue() {
        return value;
    }

    @Override
    public integer setValue(integer value) {
        integer old = this.value;
        this.value = value;
        return old;
    }
}
