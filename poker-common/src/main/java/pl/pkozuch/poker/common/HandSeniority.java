package pl.pkozuch.poker.common;

public enum HandSeniority {
    ROYAL_FLUSH(1),
    STRAIGHT_FLUSH(2),
    FOUR_OF_A_KIND(3),
    FULL_HOUSE(4),
    FLUSH(5),
    STRAIGHT(6),
    THREE_OF_KIND(7),
    TWO_PAIR(8),
    ONE_PAIR(9),
    HIGH_CARD(10);

    private final int seniorityOrder;

    HandSeniority(int seniorityOrder) {
        this.seniorityOrder = seniorityOrder;
    }

    public int getSeniorityOrder() {
        return seniorityOrder;
    }
}
