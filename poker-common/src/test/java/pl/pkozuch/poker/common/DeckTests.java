package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeckTests {

    @Test
    public void testDrawCard() {
        Deck deck = new Deck();
        Card card = deck.draw();
        Assertions.assertEquals(51, deck.count());
        Assertions.assertNotNull(card);
    }

    @Test
    public void testDrawFewCards() {
        Deck deck = new Deck();

        deck.draw(5);
        Assertions.assertEquals(47, deck.count());

        deck.draw(20);
        Assertions.assertEquals(27, deck.count());
    }

    @Test
    public void testDrawFromEmptyDeck() {
        Deck deck = new Deck();
        deck.draw(52);

        Assertions.assertEquals(0, deck.count());

        Assertions.assertThrows(RuntimeException.class, deck::draw);
    }

    @Test
    public void testNotEnoughCardsInDeck() {
        Deck deck = new Deck();

        Assertions.assertEquals(52, deck.count());

        Assertions.assertThrows(RuntimeException.class, () -> deck.draw(53));
    }

    @Test
    void testRebuildDeck() {
        Deck deck = new Deck();
        deck.draw(52);

        Assertions.assertEquals(0, deck.count());

        deck.buildDeck();

        Assertions.assertEquals(52, deck.count());
    }

    @Test
    public void testRebuildFullDeck() {
        Deck deck = new Deck();

        Assertions.assertEquals(52, deck.count());

        deck.buildDeck();

        Assertions.assertEquals(52, deck.count());
    }
}
