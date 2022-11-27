package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.TestPreparer;

import java.util.List;

public class CheckActionTests {

    @Test
    public void testCheckAction() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        gc.startGame();

        Assertions.assertFalse(p.doesCheck());

        CheckAction checkAction = new CheckAction(gc, p);
        checkAction.make();

        Assertions.assertTrue(p.doesCheck());
    }

    @Test
    public void testIfPlayerIsInactiveAfterCheck() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        List<Player> activePlayers = gc.getActivePlayers();

        Assertions.assertTrue(activePlayers.contains(p));

        CheckAction checkAction = new CheckAction(gc, p);
        checkAction.make();

        activePlayers = gc.getActivePlayers();

        Assertions.assertFalse(activePlayers.contains(p));
    }

    @Test
    public void testPlayerActiveAfterSbRaise() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        List<Player> activePlayers = gc.getActivePlayers();

        Assertions.assertTrue(activePlayers.contains(p1));

        CheckAction checkAction = new CheckAction(gc, p1);
        checkAction.make();

        activePlayers = gc.getActivePlayers();
        Assertions.assertFalse(activePlayers.contains(p1));

        RaiseAction raiseAction = new RaiseAction(gc, p2, new String[]{"10"});
        raiseAction.make();

        activePlayers = gc.getActivePlayers();
        Assertions.assertTrue(activePlayers.contains(p1));
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
    public void testCheckDuringChanging() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("RAISE 10");
        p2.setAction("CALL");
        p3.setAction("CALL");

        List<Player> activePlayers = gc.getActivePlayers();
        Assertions.assertTrue(activePlayers.contains(p1));

        /*
            Start betting round - use actions declared above:
            1. First player raise bet
            2. Second player calls

         */
        gc.startNextRound();

        //Now round state == CHANGING

        CheckAction checkAction = new CheckAction(gc, p1);
        Assertions.assertThrows(IllegalActionException.class, checkAction::make);
    }
}
