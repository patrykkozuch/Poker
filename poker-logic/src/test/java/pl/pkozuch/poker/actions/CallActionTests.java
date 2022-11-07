package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.TestPreparer;

public class CallActionTests {

    @Test
    public void testCallWithoutRaise() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        CallAction callAction = new CallAction((GameController) objects[0], (Player) objects[1]);

        Assertions.assertThrows(RuntimeException.class, callAction::make);
    }

    @Test
    public void testCallWithoutEnoughMoney() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        p2.setBalance(10);

        RaiseAction raiseAction = new RaiseAction(gc, p1, new String[]{"20"});
        raiseAction.make();

        CallAction callAction = new CallAction(gc, p2);
        Assertions.assertThrows(RuntimeException.class, callAction::make);
    }

    @Test
    public void testCallWhileChanging() {
        String actions = "RAISE 10\nRAISE 10\nRAISE 10\n";

        Object[] objects = TestPreparer.createGameControllerAndPlayers(actions);

        GameController gc = (GameController) objects[0];

        Player p1 = (Player) objects[1];

        gc.startNextRound();

        CallAction callAction = new CallAction(gc, p1);
        Assertions.assertThrows(RuntimeException.class, callAction::make);
    }

    @Test
    public void testCallActionSuccessfully() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();
        GameController gc = (GameController) objects[0];

        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        RaiseAction raiseAction = new RaiseAction(gc, p1, new String[]{"10"});
        raiseAction.make();

        CallAction callAction = new CallAction(gc, p2);
        callAction.make();

        Assertions.assertEquals(10, p2.getBetInCurrentRound());
        Assertions.assertEquals(90, p2.getBalance());
    }
}
