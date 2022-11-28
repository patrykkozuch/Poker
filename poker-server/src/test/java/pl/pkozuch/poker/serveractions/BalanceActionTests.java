package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

class BalanceActionTests {

    @Test
    void testBalanceAction() throws IllegalActionException {
        PlayerWrapperStub p = new PlayerWrapperStub();

        BalanceAction balanceAction = new BalanceAction(null, p);
        balanceAction.make();

        Assertions.assertTrue(p.getSentMessage().contains(p.getPlayer().getBalance().toString()));
    }

    @Test
    void testBalanceAction__Successful() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        new BalanceAction(s, p).make();

        Assertions.assertTrue(p.getSentMessage().contains(p.getPlayer().getBalance().toString()));

    }
}
