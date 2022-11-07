package pl.pkozuch.poker.common;

import java.util.Objects;

public class HandEntry implements Comparable<HandEntry> {

    private final Card card;
    private int priority;

    public HandEntry(Card card, int priority) {
        this.card = card;
        this.priority = priority;
    }

    public Card getCard() {
        return card;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(HandEntry o) {
        if (priority == o.priority) {
            return Integer.compare(card.value.getOrder(), o.card.value.getOrder());
        } else return -Integer.compare(priority, o.priority);
    }

    @Override
    public String toString() {
        return card.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HandEntry handEntry)) return false;
        return priority == handEntry.priority && Objects.equals(card, handEntry.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(card, priority);
    }
}
