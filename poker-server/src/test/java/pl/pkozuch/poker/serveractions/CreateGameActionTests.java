package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

class CreateGameActionTests {

    @Test
    void testCreateGameCreation__NoArgument() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateGameAction(null, p, null));
    }

    @Test
    void testCreateGameCreation__TooManyArguments() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateGameAction(null, p, new String[]{"12", "23", "45", "56"}));
    }

    @Test
    void testCreateGameCreation__AnteNotIntegral() {
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateGameAction(null, p, new String[]{"abd"}));
    }

    @Test
    void testCreateGame__Successful() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        CreateGameAction createGameAction = new CreateGameAction(server, p, new String[]{"20"});
        createGameAction.make();

        Assertions.assertTrue(server.hasGameWithID(1));
    }

    @Test
    void testCreateGame__PlaysAnotherGame() throws IllegalActionException {
        Server server = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        p.getPlayer().setBalance(100);

        CreateGameAction createGameAction = new CreateGameAction(server, p, new String[]{"20"});
        createGameAction.make();

        JoinGameAction joinGameAction = new JoinGameAction(server, p, new String[]{"1"});
        joinGameAction.make();

        Assertions.assertThrows(IllegalActionException.class, () -> new CreateGameAction(server, p, new String[]{"20"}).make());
        Assertions.assertFalse(server.hasGameWithID(2));
    }
}
