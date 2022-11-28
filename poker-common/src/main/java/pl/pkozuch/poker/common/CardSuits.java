package pl.pkozuch.poker.common;

import java.util.Arrays;
import java.util.Optional;

/**
 * Representation of CardSuit
 */
public enum CardSuits {
    /**
     * Name: Trefl, Shortcut: "C"
     */
    CLUB("Trefl", "C"),

    /**
     * Name: Karo, Shortcut: "D"
     */
    DIAMOND("Karo", "D"),

    /**
     * Name: Kier, Shortcut: "H"
     */
    HEART("Kier", "H"),

    /**
     * Name: Pik, Shortcut: "S"
     */
    SPADE("Pik", "S");

    /**
     * Name of the suit in polish
     */
    private final String name;

    /**
     * One-character long shortcut of the suit
     */
    private final String shortcut;

    CardSuits(String name, String shortcut) {
        this.name = name;
        this.shortcut = shortcut;
    }

    /**
     * Gets enum suit value by specified shortcut
     * <p>Possible values:<br>
     * 'C', 'D', 'H', 'S'
     * </p>
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
     * @return name of the suit in polish
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
