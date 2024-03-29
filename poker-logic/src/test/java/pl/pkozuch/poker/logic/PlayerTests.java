package pl.pkozuch.poker.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.PlayerStub;
import pl.pkozuch.poker.common.Card;
import pl.pkozuch.poker.common.Deck;

class PlayerTests {

    @Test
    void testBalance() {
        Player p = new PlayerStub();

        p.setBalance(100);
        p.reduceBalance(20);

        Assertions.assertEquals(80, p.getBalance());

        p.raiseBalance(20);

        Assertions.assertEquals(100, p.getBalance());
    }

    @Test
    void testCheck() {
        Player p = new PlayerStub();

        Assertions.assertFalse(p.doesCheck());

        p.check();

        Assertions.assertTrue(p.doesCheck());

        p.resetStatus();

        Assertions.assertFalse(p.doesCheck());
    }

    @Test
    void testFold() {
        Player p = new PlayerStub();

        Assertions.assertFalse(p.doesFold());

        p.fold();

        Assertions.assertTrue(p.doesFold());

        p.resetStatus();

        Assertions.assertFalse(p.doesFold());
    }

    @Test
    void testBetAllIn() {
        Player p = new PlayerStub();

        Assertions.assertFalse(p.doesBetAllIn());

        p.betAllIn();

        Assertions.assertTrue(p.doesBetAllIn());

        p.resetStatus();

        Assertions.assertFalse(p.doesBetAllIn());
    }

    @Test
    void testCardChange() {
        Player p = new PlayerStub();

        Assertions.assertFalse(p.hasChangedCards());

        p.changeCards();

        Assertions.assertTrue(p.hasChangedCards());

        p.resetStatus();

        Assertions.assertFalse(p.hasChangedCards());
    }

    @Test
    void testBet() {
        Player p = new PlayerStub();

        Assertions.assertEquals(0, p.getBetInCurrentRound());

        p.raiseBetInCurrentRound(10);

        Assertions.assertEquals(10, p.getBetInCurrentRound());

        p.raiseBetInCurrentRound(10);

        Assertions.assertEquals(20, p.getBetInCurrentRound());

        p.resetBet();

        Assertions.assertEquals(0, p.getBetInCurrentRound());
    }

    @Test
    void testCards() {
        Player p = new PlayerStub();
        Deck d = new Deck();

        Card[] cards = d.draw(5);

        p.cards = cards;

        Card[] cardsFromPlayer = p.getCards();

        Assertions.assertNotEquals(cardsFromPlayer, cards);
    }
}
