package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.NoSuchPlayerException;
import pl.pkozuch.poker.logic.TestPreparer;

public class FoldActionTests {
    @Test
    public void testFoldAction() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        Assertions.assertFalse(p.doesFold());

        FoldAction foldAction = new FoldAction(gc, p);
        foldAction.make();

        Assertions.assertTrue(p.doesFold());
    }

    @Test
    public void testIfPlayerIsActiveAfterFold__Betting() throws IllegalActionException, NoSuchPlayerException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        Assertions.assertTrue(gc.isPlayerActive(p.getId()));

        FoldAction foldAction = new FoldAction(gc, p);
        foldAction.make();

        Assertions.assertFalse(gc.isPlayerActive(p.getId()));
    }

    @Test
    public void testIfPlayerIsActiveAfterFold__Change() throws NoSuchPlayerException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("FOLD");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        Assertions.assertTrue(gc.isPlayerActive(p1.getId()));

        /*
            Start betting round - use actions declared above:
            1. First player folds
            2. Second player raise bet
            3. Third player bet the same amount asa second player

         */
        gc.startNextRound();

        //  So after this three moves, first player should be inactive.
        Assertions.assertFalse(gc.isPlayerActive(p1.getId()));
    }

    @Test
    public void testFoldDuringChanging() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("RAISE 10");
        p2.setAction("CALL");
        p3.setAction("CALL");

        /*
            Start betting round - use actions declared above:
            1. First player raise bet
            2. Second player bet the same amount as first player
            3. Third player bet the same amount as first player

         */
        gc.startNextRound();

        FoldAction foldAction = new FoldAction(gc, p1);
        Assertions.assertThrows(IllegalActionException.class, foldAction::make);
    }
}
