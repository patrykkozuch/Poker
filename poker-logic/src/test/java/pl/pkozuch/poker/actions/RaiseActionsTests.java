package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.TestPreparer;

public class RaiseActionsTests {

    @Test
    public void testRaiseActionWithoutAmount() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p = (Player) objects[1];

        Assertions.assertThrows(IllegalArgumentException.class, () -> new RaiseAction(gc, p, new String[]{}));
    }

    @Test
    public void testRaiseActionWithTooManyArguments() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p = (Player) objects[1];

        Assertions.assertThrows(IllegalArgumentException.class, () -> new RaiseAction(gc, p, new String[]{"10", "20", "30"}));
    }

    @Test
    public void testRaiseActionWithInvalidArgument() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p = (Player) objects[1];

        Assertions.assertThrows(IllegalArgumentException.class, () -> new RaiseAction(gc, p, new String[]{"a"}));
    }

    @Test
    public void testRaiseActionWithLowerAmountThanCurrentBet() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        RaiseAction raiseAction1 = new RaiseAction(gc, p1, new String[]{"20"});
        raiseAction1.make();

        RaiseAction raiseAction2 = new RaiseAction(gc, p2, new String[]{"10"});
        Assertions.assertThrows(IllegalActionException.class, raiseAction2::make);
    }

    @Test
    public void testRaiseActionWithHigherThanCurrentBalance() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        Player p1 = (Player) objects[1];
        p1.setBalance(100);

        RaiseAction raiseAction = new RaiseAction(gc, p1, new String[]{"1000"});
        Assertions.assertThrows(IllegalActionException.class, raiseAction::make);
    }

    @Test
    public void testRaiseActionDuringChanging() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("CHECK");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        gc.startNextRound();

        RaiseAction raiseAction = new RaiseAction(gc, p1, new String[]{"10"});
        Assertions.assertThrows(IllegalActionException.class, raiseAction::make);
    }

    @Test
    public void testRaiseActionSuccessfully() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        Player p = (Player) objects[1];

        RaiseAction raiseAction1 = new RaiseAction(gc, p, new String[]{"20"});
        raiseAction1.make();

        Assertions.assertEquals(20, p.getBetInCurrentRound());
        Assertions.assertEquals(20, gc.getCurrentRoundBetPerPlayer());
        Assertions.assertEquals(80, p.getBalance());
    }

    @Test
    public void testMultipleRaisesSuccessful() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        RaiseAction raiseAction1 = new RaiseAction(gc, p1, new String[]{"20"});
        raiseAction1.make();

        RaiseAction raiseAction2 = new RaiseAction(gc, p2, new String[]{"30"});
        raiseAction2.make();

        Assertions.assertEquals(30, p2.getBetInCurrentRound());
        Assertions.assertEquals(30, gc.getCurrentRoundBetPerPlayer());
        Assertions.assertEquals(70, p2.getBalance());

        RaiseAction raiseAction3 = new RaiseAction(gc, p1, new String[]{"40"});
        raiseAction3.make();

        Assertions.assertEquals(40, p1.getBetInCurrentRound());
        Assertions.assertEquals(40, gc.getCurrentRoundBetPerPlayer());
        Assertions.assertEquals(60, p1.getBalance());
    }
}
