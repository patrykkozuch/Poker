package pl.pkozuch.poker.common;

import java.util.ArrayList;
import java.util.Random;

/**
 * Deck representation - 52 cards without Jokers
 */
public class Deck {

    final ArrayList<Card> cardsInDeck = new ArrayList<>(52);
    final Random random = new Random();

    /**
     * Constructs deck of 52 cards
     */
    public Deck() {
        buildDeck();
    }

    /**
     * Rebuilds deck of cards.
     * Previous deck is cleared.
     * <b>REMEMBER: </b>Cards drawn from deck before rebuild <b>will not be destroyed.</b>
     */
    public void buildDeck() {
        cardsInDeck.clear();

        for (CardValues type : CardValues.values()) {
            for (CardSuits Suit : CardSuits.values()) {
                cardsInDeck.add(new Card(Suit, type));
            }
        }
    }

    /**
     * Draws single card from deck
     *
     * @return Drawn card
     * @throws EmptyDeckException if deck is empty
     */
    public Card draw() throws EmptyDeckException {

        if (count() == 0)
            throw new EmptyDeckException("Talia jest pusta. Nie można wylosować karty z pustej talii.");
        int drawnCardIdx = random.nextInt(0, cardsInDeck.size());

        return cardsInDeck.remove(drawnCardIdx);
    }

    /**
     * Draws multiple cards from deck
     *
     * @param number Number of cards to draw
     * @return Array of drawn cards
     * @throws EmptyDeckException When there left less than {@code  number} of cards in deck
     */
    public Card[] draw(int number) throws EmptyDeckException {
        if (cardsInDeck.size() < number)
            throw new EmptyDeckException("W talii jest za mało kart. Nie można wylosować kart.");

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
        return cardsInDeck.size();
    }
}
