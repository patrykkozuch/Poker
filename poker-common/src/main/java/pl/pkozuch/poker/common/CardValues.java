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

    /**
     * Gets enum value by specified shortcut
     * <p>Possible values:<br>
     * '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'K', 'Q', A"
     * </p>
     *
     * @param valueShortcut one character long shortcut of the suit
     * @return found enum value
     * @throws RuntimeException if invalid shortcut is provided
     */
    public static CardValues getValueByShortcut(String valueShortcut) {

        Optional<CardValues> foundValue = Arrays.stream(CardValues.values()).filter(cardValue -> cardValue.shortcut.equals(valueShortcut)).findFirst();
        if (foundValue.isPresent())
            return foundValue.get();
        else
            throw new InvalidSlugException("Value " + valueShortcut + " not found");
    }

    /**
     * Gets card value name
     * <p>
     * Needed because '10' when displayed should be marked as '10', not as 'T'.
     * </p>
     *
     * @return card value name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets card order.
     * Order is set as:
     *
     * <ul>
     *     <li>Ace is the 'lowest' (1 in order)</li>
     *     <li>2 is the 'highest' (14 in order)</li>
     * </ul>
     *
     * @return order of the card
     */
    public int getOrder() {
        return order;
    }

    /**
     * Gets one character long shortcut of the card
     *
     * @return shortcut of the card
     */
    public String getShortcut() {
        return shortcut;
    }
}
