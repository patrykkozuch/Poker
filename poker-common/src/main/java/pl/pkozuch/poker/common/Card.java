package pl.pkozuch.poker.common;

import java.util.Objects;

public class Card {
    CardValues value;
    CardSuits suit;

    Card(CardValues value, CardSuits Suit) {
        this.suit = Suit;
        this.value = value;
    }

    Card(String slug) {
        if (slug.length() != 2)
            throw new RuntimeException("Card can be created only from 2-characters long slug. Slug entered: " + slug);

        String valueString = slug.substring(1);
        String suitString = slug.substring(0, 1);

        this.suit = CardSuits.getSuitByString(suitString);
        this.value = CardValues.getValueByString(valueString);
    }

    public CardSuits getSuit() {
        return suit;
    }

    public CardValues getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.getName() + " " + suit.getName();
    }

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
