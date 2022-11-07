package pl.pkozuch.poker.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.common.Card;
import pl.pkozuch.poker.common.Deck;

public class PlayerTests {

    @Test
    public void testBalance() {
        Player p = new Player(new StreamController(System.in, System.out));
        p.setBalance(100);
        p.reduceBalance(20);
        Assertions.assertEquals(80, p.getBalance());
    }

    @Test
    public void testCheck() {
        Player p = new Player(new StreamController(System.in, System.out));

        Assertions.assertFalse(p.doesCheck());

        p.check();

        Assertions.assertTrue(p.doesCheck());
    }

    @Test
    public void testFold() {
        Player p = new Player(new StreamController(System.in, System.out));

        Assertions.assertFalse(p.doesFold());

        p.fold();

        Assertions.assertTrue(p.doesFold());
    }

    @Test
    public void testBetAllIn() {
        Player p = new Player(new StreamController(System.in, System.out));

        Assertions.assertFalse(p.doesBetAllIn());

        p.betAllIn();

        Assertions.assertTrue(p.doesBetAllIn());
    }

    @Test
    public void testCardChange() {
        Player p = new Player(new StreamController(System.in, System.out));

        Assertions.assertFalse(p.hasChangedCards());

        p.changeCards();

        Assertions.assertTrue(p.hasChangedCards());
    }

    @Test
    public void testBet() {
        Player p = new Player(new StreamController(System.in, System.out));

        Assertions.assertEquals(0, p.getBetInCurrentRound());

        p.raiseBetInCurrentRound(10);

        Assertions.assertEquals(10, p.getBetInCurrentRound());

        p.resetBet();

        Assertions.assertEquals(0, p.getBetInCurrentRound());
    }

    @Test
    public void testCards() {
        Player p = new Player(new StreamController(System.in, System.out));
        Deck d = new Deck();

        Card[] cards = d.draw(5);

        p.cards = cards;

        Card[] cardsFromPlayer = p.getCards();

        Assertions.assertNotEquals(cardsFromPlayer, cards);
    }
}
