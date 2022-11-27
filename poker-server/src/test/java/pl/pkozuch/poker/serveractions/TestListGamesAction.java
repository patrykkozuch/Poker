package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

class TestListGamesAction {

    @Test
    void testListGamesAction__TooManyArguments() {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ListGames(s, p, new String[]{"as", "as", "asd"}));
    }

    @Test
    void testListGamesAction__ShowsAllGames() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        new CreateGame(s, p, new String[]{"20"}).make();
        new CreateGame(s, p, new String[]{"30"}).make();
        new CreateGame(s, p, new String[]{"40"}).make();

        new ListGames(s, p, null).make();

        Assertions.assertTrue(p.getSentMessage().contains("Gra nr 1 (Ante 20"));
        Assertions.assertTrue(p.getSentMessage().contains("Gra nr 2 (Ante 30"));
        Assertions.assertTrue(p.getSentMessage().contains("Gra nr 3 (Ante 40"));
    }
}
