package pl.pkozuch.poker.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Hand implements Comparable<Hand> {

    final ArrayList<HandEntry> handEntries = new ArrayList<>();
    HandSeniority seniority = HandSeniority.HIGH_CARD;

    public Hand(List<Card> cards) {
        this.handEntries.addAll(cards.stream().map(card -> new HandEntry(card, 0)).toList());
        this.sortHand();
    }

    public ArrayList<Card> getALlCards() {
        return new ArrayList<>(handEntries.stream().map(HandEntry::getCard).toList());
    }

    public Card getCard(int index) {
        return handEntries.get(index).getCard();
    }

    public void setPriority(int[] priorities) {
        for (int i = 0; i < 5; i++) {
            handEntries.get(i).setPriority(priorities[i]);
        }
        sortHand();
    }

    private void sortHand() {
        Collections.sort(this.handEntries);
    }

    public HandSeniority getSeniority() {
        return this.seniority;
    }

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
        return seniority + " " + handEntries;
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
