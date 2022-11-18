package pl.pkozuch.poker.common;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

    final ArrayList<Card> deck = new ArrayList<>(52);

    /**
     * Constructs deck of 52 cards
     */
    public Deck() {
        buildDeck();
    }

    /**
     * Rebuilds deck of cards.
     * Previous deck is cleared.
     *
     * <b>REMEMBER: </b>Cards drawn from deck before rebuild <b>will not be destroyed.</b>
     */
    public void buildDeck() {
        deck.clear();

        for (CardValues type : CardValues.values()) {
            for (CardSuits Suit : CardSuits.values()) {
                deck.add(new Card(Suit, type));
            }
        }
    }

    /**
     * Draws single card from deck
     *
     * @return Drawn card
     * @throws RuntimeException if deck is empty
     */
    public Card draw() throws RuntimeException {

        if (count() == 0)
            throw new RuntimeException("Talia jest pusta. Nie można wylosować karty z pustej talii.");
        Random random = new Random();
        int drawnCardIdx = random.nextInt(0, deck.size());

        return deck.remove(drawnCardIdx);
    }

    /**
     * Draws multiple cards from deck
     *
     * @param number Number of cards to draw
     * @return Array of drawn cards
     * @throws RuntimeException When there left less than {@code  number} of cards in deck
     */
    public Card[] draw(int number) throws RuntimeException {
        if (deck.size() < number)
            throw new RuntimeException("W talii jest za mało kart. Nie można wylosować kart.");

        Card[] cards = new Card[number];

        for (int i = 0; i < number; i++)
            cards[i] = draw();

        return cards;
    }

    /**
     * Returns current number of cards in deck
     *
     * @return number of cards in deck
     */
    public int count() {
        return deck.size();
    }
}
