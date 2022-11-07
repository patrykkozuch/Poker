package pl.pkozuch.poker.common;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

    ArrayList<Card> deck = new ArrayList<>(52);

    public Deck() {
        buildDeck();
    }

    public void buildDeck() {
        deck.clear();
        
        int i = 0;
        for (CardValues type : CardValues.values()) {
            for (CardSuits Suit : CardSuits.values()) {
                deck.add(new Card(type, Suit));
            }
        }
    }

    public Card draw() throws RuntimeException {

        if (count() == 0)
            throw new RuntimeException("Talia jest pusta. Nie można wylosować karty z pustej talii.");
        Random random = new Random();
        int drawnCardIdx = random.nextInt(0, deck.size());

        return deck.remove(drawnCardIdx);
    }

    public Card[] draw(int number) {
        if (deck.size() < number)
            throw new RuntimeException("W talii jest za mało kart. Nie można wylosować kart.");

        Card[] cards = new Card[number];

        for (int i = 0; i < number; i++)
            cards[i] = draw();

        return cards;
    }

    public int count() {
        return deck.size();
    }
}
