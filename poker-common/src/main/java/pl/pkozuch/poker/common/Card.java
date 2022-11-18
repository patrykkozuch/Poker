package pl.pkozuch.poker.common;

import java.util.Objects;

/**
 * Representation of Single Card
 */
public class Card {
    CardValues value;
    CardSuits suit;

    /**
     * Constructs card by passing value and suit
     *
     * @param suit  - suit of the card
     * @param value - value of the card
     */
    Card(CardSuits suit, CardValues value) {
        this.suit = suit;
        this.value = value;
    }

    /**
     * <p>Constructs card from 2-characters long slug.</p>
     * <p>First character stands for suit, second stands for value</p>
     * <p>Possible suits:<br>
     * 'H' (Heart), 'D' (Diamond), 'S' (Spade), 'C' (Club)
     * </p>
     * <p>Possible values:<br>
     * '2', '3', '4', '5', '6', '7', '8', '9', 'T' (Ten), 'J', 'Q', 'K', 'A' (Ace)
     * </p>
     *
     * <p>Example valid slugs: H2, CT, DA</p>
     *
     * @param slug 2 characters long slug
     */
    Card(String slug) {
        if (slug.length() != 2)
            throw new RuntimeException("Card can be created only from 2-characters long slug. Slug entered: " + slug);

        String valueString = slug.substring(1);
        String suitString = slug.substring(0, 1);

        this.suit = CardSuits.getSuitByShortcut(suitString);
        this.value = CardValues.getValueByString(valueString);
    }

    /**
     * Gets card suit
     *
     * @return suit of the card
     */
    public CardSuits getSuit() {
        return suit;
    }

    /**
     * Gets card value
     *
     * @return value of the card
     */
    public CardValues getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.getName() + " " + suit.getName();
    }

    /**
     * Checks if card is <b>RIGHT</b> before other in seniority order
     * For example, King is <b>RIGHT</b> before Ace in seniority order, so method will return true
     *
     * @param other Card to compare
     * @return True only if card is <b>RIGHT</b> before other in seniority order
     */
    public boolean isBeforeInSeniorityOrder(Card other) {
        return this.value.getOrder() + 1 == other.value.getOrder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return value == card.value && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }
}
