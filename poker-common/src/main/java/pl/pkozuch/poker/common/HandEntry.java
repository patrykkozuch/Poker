package pl.pkozuch.poker.common;

import java.util.Objects;

/**
 * Card wrapper - adds priority to card
 */
public class HandEntry implements Comparable<HandEntry> {

    private final Card card;

    /**
     * Card priority - the highest, the most powerful
     */
    private int priority;

    public HandEntry(Card card, int priority) {
        this.card = card;
        this.priority = priority;
    }

    /**
     * Returns card
     *
     * @return Card
     */
    public Card getCard() {
        return card;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Comparator - first HandEntries are compared by priority, then by card order
     *
     * @param o HandEntry to compare with
     * @return comparison value
     */
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
