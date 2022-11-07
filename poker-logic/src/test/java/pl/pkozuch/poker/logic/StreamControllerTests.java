package pl.pkozuch.poker.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class StreamControllerTests {

    @ParameterizedTest
    @ValueSource(strings = {"Test", "1234", "ęśąćż"})
    public void testOutputStream(String stringToSend) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamController streamController = new StreamController(System.in, baos);

        streamController.send(stringToSend);

        Assertions.assertEquals(stringToSend, baos.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test", "1234", "ęśąćż"})
    public void testInputStream(String stringToSend) {
        ByteArrayInputStream bais = new ByteArrayInputStream((stringToSend + '\n').getBytes());

        System.setIn(bais);

        StreamController streamController = new StreamController(System.in, System.out);

        Assertions.assertEquals(stringToSend, streamController.readLine());
    }
}
