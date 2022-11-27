package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

class TestQuitGameAction {

    @Test
    void testQuitGameAction__TooManyArguments() {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new QuitGame(s, p, new String[]{"as", "as", "asd"}));
    }

    @Test
    void testQuitGameAction__NotInGame() {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalActionException.class, () -> new QuitGame(s, p, null).make());
    }

    @Test
    void testQuitGameAction__QuitDuringGame() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        PlayerWrapperStub p2 = new PlayerWrapperStub();

        new CreateGame(s, p, new String[]{"1"}).make();
        new JoinGame(s, p, new String[]{"1"}).make();
        new JoinGame(s, p2, new String[]{"1"}).make();
        new StartGame(s, p, null).make();

        Assertions.assertThrows(IllegalActionException.class, () -> new QuitGame(s, p, null).make());
    }

    @Test
    void testQuitGameAction__BringAnteBack() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);

        new CreateGame(s, p, new String[]{"10"}).make();
        new JoinGame(s, p, new String[]{"1"}).make();

        Assertions.assertEquals(90, p.getPlayer().getBalance());

        new QuitGame(s, p, null).make();
        Assertions.assertEquals(100, p.getPlayer().getBalance());
    }

    @Test
    void testQuitGameAction__ChangeHostID() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        PlayerWrapperStub p2 = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);

        new CreateGame(s, p, new String[]{"10"}).make();
        new JoinGame(s, p, new String[]{"1"}).make();
        new JoinGame(s, p2, new String[]{"1"}).make();

        Assertions.assertEquals(p.getPlayer().getId(), s.getGame(p.getGameID()).getHostID());

        new QuitGame(s, p, null).make();

        Assertions.assertEquals(p2.getPlayer().getId(), s.getGame(p2.getGameID()).getHostID());
    }

    @Test
    void testQuitGameAction__ChangeHostIDToNull() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        p.getPlayer().setBalance(100);

        new CreateGame(s, p, new String[]{"10"}).make();
        new JoinGame(s, p, new String[]{"1"}).make();

        Assertions.assertEquals(p.getPlayer().getId(), s.getGame(1).getHostID());

        new QuitGame(s, p, null).make();

        Assertions.assertNull(s.getGame(1).getHostID());
    }
}
