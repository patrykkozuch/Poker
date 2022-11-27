package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.TestPreparer;

class CallActionTests {

    @Test
    void testCallWithoutRaise() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        CallAction callAction = new CallAction((GameController) objects[0], (Player) objects[1]);

        Assertions.assertThrows(IllegalActionException.class, callAction::make);
    }

    @Test
    void testCallWithoutEnoughMoney() throws IllegalActionException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];

        p2.setBalance(10);

        RaiseAction raiseAction = new RaiseAction(gc, p1, new String[]{"20"});
        raiseAction.make();

        CallAction callAction = new CallAction(gc, p2);
        Assertions.assertThrows(IllegalActionException.class, callAction::make);
    }

    @Test
    void testCallWhileChanging() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("RAISE 10");
        p2.setAction("CALL");
        p3.setAction("CALL");

        gc.startNextRound();

        CallAction callAction = new CallAction(gc, p1);
        Assertions.assertThrows(IllegalActionException.class, callAction::make);
    }

    @Test
    void testCallActionSuccessfully() throws IllegalActionException {
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
