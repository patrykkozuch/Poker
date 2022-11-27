package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class HandEntryTests {

    private static Stream<Arguments> provideHandEntries() {
        return Stream.of(
                Arguments.of(new HandEntry(new Card("HA"), 0), new HandEntry(new Card("SQ"), 0)),
                Arguments.of(new HandEntry(new Card("HA"), 1), new HandEntry(new Card("SA"), 0)),
                Arguments.of(new HandEntry(new Card("H2"), 1), new HandEntry(new Card("SA"), 0))
        );
    }

    @ParameterizedTest
    @MethodSource("provideHandEntries")
    void testHandEntriesComparison__EqualPriority(HandEntry h1, HandEntry h2) {

        Assertions.assertEquals(-1, h1.compareTo(h2));
        Assertions.assertEquals(1, h2.compareTo(h1));
    }
}
