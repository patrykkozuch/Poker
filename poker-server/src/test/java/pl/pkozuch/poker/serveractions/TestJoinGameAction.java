package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

public class TestJoinGameAction {

    @Test
    void testJoinGameCreation__NoArgument() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new JoinGame(null, p, null));
    }

    @Test
    void testJoinGameCreation__TooManyArguments() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new JoinGame(null, p, new String[]{"12", "23", "45", "56"}));
    }

    @Test
    void testJoinGameCreation__GameNumberNoIntegral() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new JoinGame(null, p, new String[]{"abd"}));
    }

    @Test
    void testJoinGame__Successful() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);

        CreateGame createGame = new CreateGame(server, p, new String[]{"20"});
        createGame.make();

        JoinGame joinGame = new JoinGame(server, p, new String[]{"1"});
        joinGame.make();

        Assertions.assertEquals(1, p.getGameID());
        Assertions.assertTrue(server.getGame(1).hasPlayerWithID(p.getPlayer().getId()));
    }

    @Test
    void testJoinGame__NotEnoughMoneyForAnte() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        p.getPlayer().setBalance(100);

        CreateGame createGame = new CreateGame(server, p, new String[]{"1000"});
        createGame.make();

        JoinGame joinGame = new JoinGame(server, p, new String[]{"1"});
        Assertions.assertThrows(IllegalActionException.class, joinGame::make);
    }

    @Test
    void testJoinGame__PlaysAnotherGame() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        p.getPlayer().setBalance(100);

        CreateGame createGame = new CreateGame(server, p, new String[]{"20"});
        createGame.make();

        JoinGame joinGame = new JoinGame(server, p, new String[]{"1"});
        joinGame.make();

        Assertions.assertThrows(IllegalActionException.class, () -> new JoinGame(server, p, new String[]{"1"}).make());
    }

    @Test
    void testJoinGame__GameDoesNotExist() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        p.getPlayer().setBalance(100);

        CreateGame createGame = new CreateGame(server, p, new String[]{"20"});
        createGame.make();

        Assertions.assertThrows(IllegalActionException.class, () -> new JoinGame(server, p, new String[]{"5"}).make());
    }
}
