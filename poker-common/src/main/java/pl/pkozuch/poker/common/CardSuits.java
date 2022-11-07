package pl.pkozuch.poker.common;

import java.util.Arrays;
import java.util.Optional;

public enum CardSuits {
    CLUB("Trefl", "C"),
    DIAMOND("Karo", "D"),
    HEART("Kier", "H"),
    SPADE("Pik", "S");

    private final String name;
    private final String shortcut;

    CardSuits(String name, String shortcut) {
        this.name = name;
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public static CardSuits getSuitByString(String suitShortcut) {

        Optional<CardSuits> foundValue = Arrays.stream(CardSuits.values())
                .filter(cardSuit -> cardSuit.shortcut.equals(suitShortcut))
                .findFirst();

        if (foundValue.isPresent())
            return foundValue.get();
        else
            throw new RuntimeException("Suit " + suitShortcut + " not found");
    }
}
