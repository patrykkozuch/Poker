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

    /**
     * Gets enum suit value by specified shortcut
     * Possible values:<br>
     * 'C', 'D', 'H', 'S'
     *
     * @param suitShortcut one character long shortcut of the suit
     * @return found enum suit value
     * @throws InvalidShortcutException if invalid shortcut is provided
     */
    public static CardSuits getSuitByShortcut(String suitShortcut) throws InvalidShortcutException {

        Optional<CardSuits> foundValue = Arrays.stream(CardSuits.values())
                .filter(cardSuit -> cardSuit.shortcut.equals(suitShortcut))
                .findFirst();

        if (foundValue.isPresent())
            return foundValue.get();
        else
            throw new InvalidShortcutException("Suit " + suitShortcut + " not found");
    }

    /**
     * Gets name of the suit
     *
     * @return name of the suit
     */
    public String getName() {
        return name;
    }

    /**
     * Gets one character long shortcut of the suit
     *
     * @return shortcut of the suit
     */
    public String getShortcut() {
        return shortcut;
    }
}
