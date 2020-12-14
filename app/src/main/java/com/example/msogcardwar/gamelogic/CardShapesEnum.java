package com.example.msogcardwar.gamelogic;

import java.util.HashMap;
import java.util.Map;

public enum CardShapesEnum {
    HEART(1),
    SPADE(2),
    DIAMOND(3),
    CLUB(4);


    private final int cardShapesEnum;

    private static final Map<Integer, CardShapesEnum> map = new HashMap<>();

    static {
        for (CardShapesEnum legEnum : CardShapesEnum.values()) {
            map.put(legEnum.cardShapesEnum, legEnum);
        }
    }

    CardShapesEnum(final int leg) {
        cardShapesEnum = leg;
    }

    public static CardShapesEnum valueOf(int legNo) {
        return map.get(legNo);
    }
}
