package pl.pkozuch.poker.common;

import java.util.Arrays;
import java.util.Optional;

public enum CardValues {
    ACE("AS", "A", 1),
    KING("K", "K", 2),
    QUEEN("Q", "Q", 3),
    JAKE("J", "J", 4),
    TEN("T", "T", 5),
    NINE("9", "9", 6),
    EIGHT("8", "8", 7),
    SEVEN("7", "7", 8),
    SIX("6", "6", 9),
    FIVE("5", "5", 10),
    FOUR("4", "4", 11),
    THREE("3", "3", 12),
    TWO("2", "2", 13);

    private final String name;
    private final int order;
    private final String shortcut;

    CardValues(String name, String shortcut, int order) {
        this.name = name;
        this.order = order;
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getShortcut() {
        return shortcut;
    }

    public static CardValues getValueByString(String value) {

        Optional<CardValues> foundValue = Arrays.stream(CardValues.values()).filter(cardValue -> cardValue.shortcut.equals(value)).findFirst();
        if (foundValue.isPresent())
            return foundValue.get();
        else
            throw new RuntimeException("Value " + value + " not found");
    }
}
