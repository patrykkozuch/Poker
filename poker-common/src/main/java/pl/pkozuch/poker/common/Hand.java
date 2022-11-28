package pl.pkozuch.poker.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Poker hand representation, contains cards with its priorities and seniority
 */
public class Hand implements Comparable<Hand> {

    ArrayList<HandEntry> handEntries = new ArrayList<>();

    /**
     * Seniority by default is set to {@link HandSeniority#HIGH_CARD}. After {@link Hand#check()} set to checked seniority.
     */
    HandSeniority seniority = HandSeniority.HIGH_CARD;

    /**
     * Constructs hand with {@code cards} provided
     *
     * @param cards List of cards drawn from Deck
     */
    public Hand(List<Card> cards) {
        this.handEntries.addAll(cards.stream().map(card -> new HandEntry(card, 0)).toList());
        this.sortHand();
    }

    /**
     * Gets copy of cards from hand
     *
     * @return List of cards
     */
    public List<Card> getALlCards() {
        return new ArrayList<>(handEntries.stream().map(HandEntry::getCard).toList());
    }

    /**
     * Gets card by index
     *
     * @param index Index of card to be get
     * @return card object
     */
    public Card getCard(int index) {
        return handEntries.get(index).getCard();
    }

    /**
     * <p>Sets priorities of cards - index of priority is the same as card index.</p>
     * <p>After setting priorities, hand is sorted with use of this priorities.</p>
     *
     * @param priorities Array of priorities
     */
    public void setPriority(int[] priorities) {
        for (int i = 0; i < 5; i++) {
            handEntries.get(i).setPriority(priorities[i]);
        }
        sortHand();
    }

    /**
     * Sorts hand
     */
    private void sortHand() {
        Collections.sort(this.handEntries);
    }

    /**
     * Gets hand seniority
     *
     * @return hand seniority. If hand wasn't checked, returns {@code HandSeniority.HIGH_CARD} by default
     */
    public HandSeniority getSeniority() {
        return this.seniority;
    }

    /**
     * Checks hand with use of {@link HandChecker}
     * After check, {@link Hand#seniority} is set to checked seniority
     *
     * @return {@code this}
     */
    public Hand check() {
        this.seniority = HandChecker.check(this);
        return this;
    }

    @Override
    public int compareTo(Hand o) {
        if (this.seniority == o.seniority) {
            for (int i = 0; i < 5; i++) {
                if (this.getCard(i).value != o.getCard(i).value)
                    return Integer.compare(this.getCard(i).value.getOrder(), o.getCard(i).value.getOrder());
            }
            return 0;
        } else return Integer.compare(this.seniority.getSeniorityOrder(), o.seniority.getSeniorityOrder());
    }

    @Override
    public String toString() {
        return seniority + " " + handEntries.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hand hand)) return false;
        return Objects.equals(handEntries, hand.handEntries) && seniority == hand.seniority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handEntries, seniority);
    }
}
