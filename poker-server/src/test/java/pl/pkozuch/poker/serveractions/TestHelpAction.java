package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.server.Server;

public class TestHelpAction {
    @Test
    void testHelpAction__TooManyArguments() {
        Server s = new Server();
        PlayerWrapperStub p = new PlayerWrapperStub();
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Help(s, p, new String[]{"as", "as", "asd"}));
    }
}
