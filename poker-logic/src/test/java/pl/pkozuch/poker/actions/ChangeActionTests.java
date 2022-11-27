package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.common.Card;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.TestPreparer;

public class ChangeActionTests {
    @Test
    public void testWrongCardNumber() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChangeAction(gc, p, new String[]{"-1"}));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChangeAction(gc, p, new String[]{"6"}));
    }

    @Test
    public void testChangeCardDuringOtherStateThanChanging() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        ChangeAction changeAction = new ChangeAction(gc, p, new String[]{"1"});
        Assertions.assertThrows(IllegalActionException.class, changeAction::make);
    }

    @Test
    public void testChangeSuccessfully() throws IllegalActionException {

        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("RAISE 10");
        p2.setAction("CALL");
        p3.setAction("CALL");

        gc.startNextRound();

        Card[] p1CardsBeforeChange = p1.getCards();
        Card[] p2CardsBeforeChange = p2.getCards();

        //Providing 0 should not change any card
        ChangeAction changeAction1 = new ChangeAction(gc, p1, new String[]{"0"});
        changeAction1.make();

        ChangeAction changeAction2 = new ChangeAction(gc, p2, new String[]{"1", "2", "3", "4", "5"});
        changeAction2.make();

        Card[] p1CardsAfterChange = p1.getCards();
        Card[] p2CardsAfterChange = p2.getCards();

        Assertions.assertTrue(p1.hasChangedCards());
        Assertions.assertTrue(p2.hasChangedCards());

        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(p1CardsBeforeChange[i], p1CardsAfterChange[i]);
            Assertions.assertNotEquals(p2CardsBeforeChange[i], p2CardsAfterChange[i]);
        }
    }

    @Test
    public void testChangeWithInvalidArgumentType() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        PlayerStub p = (PlayerStub) objects[1];

        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChangeAction(gc, p, new String[]{"b"}));
    }
}
