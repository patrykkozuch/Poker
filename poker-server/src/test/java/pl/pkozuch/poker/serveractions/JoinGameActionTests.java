package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

class JoinGameActionTests {

    @Test
    void testJoinGameCreation__NoArgument() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new JoinGameAction(null, p, null));
    }

    @Test
    void testJoinGameCreation__TooManyArguments() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new JoinGameAction(null, p, new String[]{"12", "23", "45", "56"}));
    }

    @Test
    void testJoinGameCreation__GameNumberNoIntegral() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new JoinGameAction(null, p, new String[]{"abd"}));
    }

    @Test
    void testJoinGame__Successful() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);

        CreateGameAction createGameAction = new CreateGameAction(server, p, new String[]{"20"});
        createGameAction.make();

        JoinGameAction joinGameAction = new JoinGameAction(server, p, new String[]{"1"});
        joinGameAction.make();

        Assertions.assertEquals(1, p.getGameID());
        Assertions.assertTrue(server.getGame(1).hasPlayerWithID(p.getPlayer().getId()));
    }

    @Test
    void testJoinGame__NotEnoughMoneyForAnte() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        p.getPlayer().setBalance(100);

        CreateGameAction createGameAction = new CreateGameAction(server, p, new String[]{"1000"});
        createGameAction.make();

        JoinGameAction joinGameAction = new JoinGameAction(server, p, new String[]{"1"});
        Assertions.assertThrows(IllegalActionException.class, joinGameAction::make);
    }

    @Test
    void testJoinGame__PlaysAnotherGame() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        p.getPlayer().setBalance(100);

        CreateGameAction createGameAction = new CreateGameAction(server, p, new String[]{"20"});
        createGameAction.make();

        JoinGameAction joinGameAction = new JoinGameAction(server, p, new String[]{"1"});
        joinGameAction.make();

        Assertions.assertThrows(IllegalActionException.class, () -> new JoinGameAction(server, p, new String[]{"1"}).make());
    }

    @Test
    void testJoinGame__GameDoesNotExist() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        p.getPlayer().setBalance(100);

        CreateGameAction createGameAction = new CreateGameAction(server, p, new String[]{"20"});
        createGameAction.make();

        Assertions.assertThrows(IllegalActionException.class, () -> new JoinGameAction(server, p, new String[]{"5"}).make());
    }
}
