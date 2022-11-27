package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

public class TestStartGameAction {
    @Test
    void testStartGameAction__TooManyArguments() {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StartGame(s, p, new String[]{"as", "as", "asd"}));
    }

    @Test
    void testStartGame__NotInLobby() {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalActionException.class, () -> new StartGame(s, p, null).make());
    }

    @Test
    void testStartGame__NotAHost() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        PlayerWrapperStub p2 = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);
        p2.getPlayer().setBalance(100);

        new CreateGame(s, p, new String[]{"10"}).make();
        new JoinGame(s, p, new String[]{"1"}).make();
        new JoinGame(s, p2, new String[]{"1"}).make();

        Assertions.assertThrows(IllegalActionException.class, () -> new StartGame(s, p2, null).make());
    }

    @Test
    void testStartGame__Successful() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);

        new CreateGame(s, p, new String[]{"10"}).make();
        new JoinGame(s, p, new String[]{"1"}).make();

        new StartGame(s, p, null).make();

        Assertions.assertEquals("W trakcie", s.getGame(1).getStatus());
    }
}
