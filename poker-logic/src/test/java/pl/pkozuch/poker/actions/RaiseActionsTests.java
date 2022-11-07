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

        Assertions.assertThrows(RuntimeException.class, () -> {
            new RaiseAction(gc, p, new String[]{});
        });
    }

    @Test
    public void testRaiseActionWithTooManyArguments() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p = (Player) objects[1];

        Assertions.assertThrows(RuntimeException.class, () -> {
            new RaiseAction(gc, p, new String[]{"10", "20", "30"});
        });
    }

    @Test
    public void testRaiseActionWithInvalidArgument() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p = (Player) objects[1];

        Assertions.assertThrows(RuntimeException.class, () -> {
            new RaiseAction(gc, p, new String[]{"a"});
        });
    }

    @Test
    public void testRaiseActionWithLowerAmountThanCurrentBet() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        RaiseAction raiseAction1 = new RaiseAction(gc, p1, new String[]{"20"});
        raiseAction1.make();

        RaiseAction raiseAction2 = new RaiseAction(gc, p2, new String[]{"10"});
        Assertions.assertThrows(RuntimeException.class, raiseAction2::make);
    }

    @Test
    public void testRaiseActionDuringChanging() {
        String actions = "RAISE 10\nRAISE 10\nRAISE 10\n";

        Object[] objects = TestPreparer.createGameControllerAndPlayers(actions);

        GameController gc = (GameController) objects[0];
        Player p = (Player) objects[1];

        gc.startNextRound();

        RaiseAction raiseAction = new RaiseAction(gc, p, new String[]{"10"});
        Assertions.assertThrows(RuntimeException.class, raiseAction::make);
    }

    @Test
    public void testRaiseActionSuccessfully() {
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
    public void testMultipleRaisesSuccessful() {
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
