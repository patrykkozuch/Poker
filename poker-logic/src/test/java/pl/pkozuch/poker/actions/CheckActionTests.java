package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.NoSuchPlayerException;
import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.TestPreparer;

public class CheckActionTests {

    @Test
    public void testCheckAction() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        Assertions.assertFalse(p.doesCheck());

        CheckAction checkAction = new CheckAction(gc, p);
        checkAction.make();

        Assertions.assertTrue(p.doesCheck());
    }

    @Test
    public void testIfPlayerIsInactiveAfterCheck() throws IllegalActionException, NoSuchPlayerException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        Assertions.assertTrue(gc.isPlayerActive(p.getId()));

        CheckAction checkAction = new CheckAction(gc, p);
        checkAction.make();

        Assertions.assertFalse(gc.isPlayerActive(p.getId()));
    }

    @Test
    public void testPlayerActiveAfterSbRaise() throws IllegalActionException, NoSuchPlayerException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        Assertions.assertTrue(gc.isPlayerActive(p1.getId()));

        CheckAction checkAction = new CheckAction(gc, p1);
        checkAction.make();

        Assertions.assertFalse(gc.isPlayerActive(p1.getId()));

        RaiseAction raiseAction = new RaiseAction(gc, p2, new String[]{"10"});
        raiseAction.make();

        Assertions.assertTrue(gc.isPlayerActive(p1.getId()));
    }

    @Test
    public void testCheckAfterRaise() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        RaiseAction raiseAction = new RaiseAction(gc, p2, new String[]{"10"});
        raiseAction.make();

        CheckAction checkAction = new CheckAction(gc, p1);
        Assertions.assertThrows(IllegalActionException.class, checkAction::make);
    }

    @Test
    public void testCheckDuringChanging() throws NoSuchPlayerException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("CHECK");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        Assertions.assertTrue(gc.isPlayerActive(p1.getId()));

        gc.startNextRound();

        //Now round state == CHANGING

        CheckAction checkAction = new CheckAction(gc, p1);
        Assertions.assertThrows(IllegalActionException.class, checkAction::make);
    }
}
