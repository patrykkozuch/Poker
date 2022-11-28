package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

class StartGameActionTests {
    @Test
    void testStartGame__NotInLobby() {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalActionException.class, () -> new StartGameAction(s, p).make());
    }

    @Test
    void testStartGame__NotAHost() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        PlayerWrapperStub p2 = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);
        p2.getPlayer().setBalance(100);

        new CreateGameAction(s, p, new String[]{"10"}).make();
        new JoinGameAction(s, p, new String[]{"1"}).make();
        new JoinGameAction(s, p2, new String[]{"1"}).make();

        Assertions.assertThrows(IllegalActionException.class, () -> new StartGameAction(s, p2).make());
    }

    @Test
    void testStartGame__Successful() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);

        new CreateGameAction(s, p, new String[]{"10"}).make();
        new JoinGameAction(s, p, new String[]{"1"}).make();

        new StartGameAction(s, p).make();

        Assertions.assertEquals("W trakcie", s.getGame(1).getStatus());
    }
}
