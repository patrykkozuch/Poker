package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.NoSuchPlayerException;
import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.TestPreparer;

class AllInActionTests {

    @Test
    void testAllInAction() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        Integer balanceBeforeAllIn = p.getBalance();

        Assertions.assertFalse(p.doesBetAllIn());

        AllInAction allInAction = new AllInAction(gc, p);
        allInAction.make();

        Assertions.assertTrue(p.doesBetAllIn());
        Assertions.assertEquals(balanceBeforeAllIn, p.getBetInCurrentRound());
        Assertions.assertEquals(0, p.getBalance());
        Assertions.assertEquals(balanceBeforeAllIn, gc.getCurrentRoundBetPerPlayer());
    }

    @Test
    void testIsPlayerInactiveAfterAllIn() throws IllegalActionException, NoSuchPlayerException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        Assertions.assertTrue(gc.isPlayerActive(p.getId()));

        AllInAction allInAction = new AllInAction(gc, p);
        allInAction.make();

        Assertions.assertFalse(gc.isPlayerActive(p.getId()));
    }

    @Test
    void testCurrentBetPerPlayerDoesNotRaiseIfBetLowerThanCurrentBet() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        p2.setBalance(30);

        RaiseAction raiseAction = new RaiseAction(gc, p1, new String[]{"100"});
        raiseAction.make();

        AllInAction allInAction = new AllInAction(gc, p2);
        allInAction.make();

        Assertions.assertEquals(100, gc.getCurrentRoundBetPerPlayer());
    }

    @Test
    void testCheckDuringChanging() throws NoSuchPlayerException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("RAISE 10");
        p2.setAction("CALL");
        p3.setAction("CALL");

        Assertions.assertTrue(gc.isPlayerActive(p1.getId()));

        /*
            Start betting round - use actions declared above:
            1. First player raise bet
            2. Second player calls

         */
        gc.startNextRound();

        //Now round state == CHANGING

        AllInAction allInAction = new AllInAction(gc, p1);
        Assertions.assertThrows(IllegalActionException.class, allInAction::make);
    }
}
