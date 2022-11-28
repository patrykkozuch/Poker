package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

class ListGamesActionTests {

    @Test
    void testListGamesAction__ShowsAllGames() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        new CreateGameAction(s, p, new String[]{"20"}).make();
        new CreateGameAction(s, p, new String[]{"30"}).make();
        new CreateGameAction(s, p, new String[]{"40"}).make();

        new ListGamesAction(s, p).make();

        Assertions.assertTrue(p.getSentMessage().contains("Gra nr 1 (Ante 20"));
        Assertions.assertTrue(p.getSentMessage().contains("Gra nr 2 (Ante 30"));
        Assertions.assertTrue(p.getSentMessage().contains("Gra nr 3 (Ante 40"));
    }
}
