package pl.pkozuch.poker.common;

/**
 * HandSeniority representation.
 */
public enum HandSeniority {
    /**
     * Order: 1
     */
    ROYAL_FLUSH(1),

    /**
     * Order: 2
     */
    STRAIGHT_FLUSH(2),

    /**
     * Order: 3
     */
    FOUR_OF_A_KIND(3),

    /**
     * Order: 4
     */
    FULL_HOUSE(4),

    /**
     * Order: 5
     */
    FLUSH(5),

    /**
     * Order: 6
     */
    STRAIGHT(6),

    /**
     * Order: 7
     */
    THREE_OF_KIND(7),

    /**
     * Order: 8
     */
    TWO_PAIR(8),

    /**
     * Order: 9
     */
    ONE_PAIR(9),

    /**
     * Order: 10
     */
    HIGH_CARD(10);

    /**
     * Order of a seniority:
     * <ul>
     *     <li>ROYAL_FLUSH has order 1 (the lowest)</li>
     *     <li>HIGH_CARD has order 10 (the highest)</li>
     * </ul>
     */
    private final int seniorityOrder;

    HandSeniority(int seniorityOrder) {
        this.seniorityOrder = seniorityOrder;
    }

    public int getSeniorityOrder() {
        return seniorityOrder;
    }
}
