package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;

public class testBalanceAction {

    @Test
    void testBalanceAction() throws IllegalActionException {
        PlayerWrapperStub p = new PlayerWrapperStub();

        Balance balance = new Balance(null, p, null);
        balance.make();

        Assertions.assertTrue(p.getSentMessage().contains(p.getPlayer().getBalance().toString()));
    }
}
