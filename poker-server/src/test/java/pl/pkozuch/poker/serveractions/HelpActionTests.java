package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.Server;

class HelpActionTests {
    @Test
    void testHelpAction__Successful() throws IllegalActionException {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();

        new HelpAction(s, p).make();

        Assertions.assertTrue(p.getSentMessage().contains("JOIN"));
        Assertions.assertTrue(p.getSentMessage().contains("CREATE"));
        Assertions.assertTrue(p.getSentMessage().contains("HELP"));
    }
}
