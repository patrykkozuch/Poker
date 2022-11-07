package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.TestPreparer;

import java.util.ArrayList;

public class FoldActionTests {
    @Test
    public void testFoldAction() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p = (Player) objects[1];

        Assertions.assertFalse(p.doesFold());

        FoldAction foldAction = new FoldAction(gc, p);
        foldAction.make();

        Assertions.assertTrue(p.doesFold());
    }

    @Test
    public void testIfPlayerIsActiveAfterFold__Betting() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gc = (GameController) objects[0];
        Player p = (Player) objects[1];

        ArrayList<Player> activePlayers = gc.getActivePlayers();

        Assertions.assertTrue(activePlayers.contains(p));

        FoldAction foldAction = new FoldAction(gc, p);
        foldAction.make();

        activePlayers = gc.getActivePlayers();

        Assertions.assertFalse(activePlayers.contains(p));
    }

    @Test
    public void testIfPlayerIsActiveAfterFold__Change() {
        String actions = "FOLD\nRAISE 10\nCALL\n";
        Object[] objects = TestPreparer.createGameControllerAndPlayers(actions);

        GameController gc = (GameController) objects[0];
        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];
        Player p3 = (Player) objects[3];

        ArrayList<Player> activePlayers = gc.getActivePlayers();
        Assertions.assertTrue(activePlayers.contains(p1));

        /*
            Start betting round - use actions declared above:
            1. First player folds
            2. Second player raise bet
            3. Third player bet the same amount asa second player

         */
        gc.startNextRound();

        //  So after this three moves, first player should be inactive.
        activePlayers = gc.getActivePlayers();
        Assertions.assertFalse(activePlayers.contains(p1));
    }

    @Test
    public void testFoldDuringChanging() {
        String actions = "RAISE 10\nCALL\nCALL\n";
        Object[] objects = TestPreparer.createGameControllerAndPlayers(actions);

        GameController gc = (GameController) objects[0];
        Player p1 = (Player) objects[1];
        Player p2 = (Player) objects[2];
        Player p3 = (Player) objects[3];

        /*
            Start betting round - use actions declared above:
            1. First player raise bet
            2. Second player bet the same amount as first player
            3. Third player bet the same amount as first player

         */
        gc.startNextRound();

        FoldAction foldAction = new FoldAction(gc, p1);
        Assertions.assertThrows(RuntimeException.class, foldAction::make);
    }
}
