package pl.pkozuch.poker.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.actions.PlayerStub;

import java.util.List;

class GameTests {

    @Test
    void testAddPlayer() throws IllegalActionException {
        Game game = new Game(0, 0);

        Player p = new PlayerStub();

        game.addPlayer(p);

        Assertions.assertTrue(game.hasPlayerWithID(p.getId()));
    }

    @Test
    void testAddMultiplePlayers() throws IllegalActionException {
        Game game = new Game(0, 0);

        Player p1 = new PlayerStub();
        Player p2 = new PlayerStub();
        Player p3 = new PlayerStub();
        Player p4 = new PlayerStub();
        Player p5 = new PlayerStub();

        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.addPlayer(p4);

        List<Player> playerList = game.getAllPlayers();

        Assertions.assertTrue(playerList.contains(p1));
        Assertions.assertTrue(playerList.contains(p2));
        Assertions.assertTrue(playerList.contains(p3));
        Assertions.assertTrue(playerList.contains(p4));

        Assertions.assertThrows(IllegalActionException.class, () -> game.addPlayer(p5));
    }

    @Test
    void testHostIDIsSet() throws IllegalActionException {
        Game game = new Game(0, 0);

        Player p = new PlayerStub();

        game.addPlayer(p);

        Assertions.assertEquals(p.getId(), game.getHostID());
    }

    @Test
    void testHostIDDoesNotChangeWhenPlayerJoins() throws IllegalActionException {
        Game game = new Game(0, 0);

        Player p1 = new PlayerStub();
        Player p2 = new PlayerStub();

        game.addPlayer(p1);
        Assertions.assertEquals(game.getHostID(), p1.getId());

        game.addPlayer(p2);
        Assertions.assertEquals(game.getHostID(), p1.getId());
    }

    @Test
    void testRemovePlayerSuccessful() throws IllegalActionException, NoSuchPlayerException {
        Game game = new Game(0, 0);

        Player p1 = new PlayerStub();

        game.addPlayer(p1);
        Assertions.assertTrue(game.hasPlayerWithID(p1.getId()));

        game.removePlayer(p1.getId());
        Assertions.assertFalse(game.hasPlayerWithID(p1.getId()));
    }

    @Test
    void testRemovePlayerNoPlayerWithID() {
        Game game = new Game(0, 0);

        Assertions.assertThrows(NoSuchPlayerException.class, () -> game.removePlayer(0));
    }

    //TODO: check if host ID changes after user quit
}
