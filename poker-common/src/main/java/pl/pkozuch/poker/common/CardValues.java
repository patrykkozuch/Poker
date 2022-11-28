package pl.pkozuch.poker.common;

import java.util.Arrays;
import java.util.Optional;

public enum CardValues {
    /**
     * Name: AS, shortcut: A, order: 1
     */
    ACE("AS", "A", 1),

    /**
     * Name: K, shortcut: K, order: 2
     */
    KING("K", "K", 2),

    /**
     * Name: Q, shortcut: Q, order: 3
     */
    QUEEN("Q", "Q", 3),

    /**
     * Name: J, shortcut: J, order: 4
     */
    JAKE("J", "J", 4),

    /**
     * Name: T, shortcut: T, order: 5
     */
    TEN("T", "T", 5),

    /**
     * Name: 9, shortcut: 9, order: 6
     */
    NINE("9", "9", 6),

    /**
     * Name: 8, shortcut: 8, order: 7
     */
    EIGHT("8", "8", 7),

    /**
     * Name: 7, shortcut: 7, order: 8
     */
    SEVEN("7", "7", 8),

    /**
     * Name: 6, shortcut: 6, order: 9
     */
    SIX("6", "6", 9),

    /**
     * Name: 5, shortcut: 5, order: 10
     */
    FIVE("5", "5", 10),

    /**
     * Name: 4, shortcut: 4, order: 11
     */
    FOUR("4", "4", 11),

    /**
     * Name: 3, shortcut: 3, order: 12
     */
    THREE("3", "3", 12),

    /**
     * Name: 2, shortcut: 2, order: 13
     */
    TWO("2", "2", 13);

    /**
     * Short name of value
     */
    private final String name;

    /**
     * Order of the value - ACE has the lowest order, 2 has the biggest
     */
    private final int order;

    /**
     * Shortcut of value - normally the same as name, except:
     * <ul>
     *     <li>ACE has shortcut "A"</li>
     *     <li>10 has shortcut "T"</li>
     * </ul>
     */
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
     * @throws InvalidShortcutException if invalid shortcut is provided
     */
    public static CardValues getValueByShortcut(String valueShortcut) {

        Optional<CardValues> foundValue = Arrays.stream(CardValues.values()).filter(cardValue -> cardValue.shortcut.equals(valueShortcut)).findFirst();
        if (foundValue.isPresent())
            return foundValue.get();
        else
            throw new InvalidShortcutException("Value " + valueShortcut + " not found");
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
     * Check {@link CardValues#order } for details
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
