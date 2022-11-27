package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

public class testBalanceAction {

    @Test
    void testBalanceAction() throws IllegalActionException {
        PlayerWrapperStub p = new PlayerWrapperStub();

        Balance balance = new Balance(null, p, null);
        balance.make();

        Assertions.assertTrue(p.getSentMessage().contains(p.getPlayer().getBalance().toString()));
    }

    @Test
    void testBalanceAction__Successful() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        new Balance(s, p, null).make();

        Assertions.assertTrue(p.getSentMessage().contains(p.getPlayer().getBalance().toString()));

    }
}
