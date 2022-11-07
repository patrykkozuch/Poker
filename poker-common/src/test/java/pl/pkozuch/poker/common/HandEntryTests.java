package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HandEntryTests {

    @Test
    public void testHandEntriesComparison__EqualPriority() {
        HandEntry h1 = new HandEntry(new Card("HA"), 0);
        HandEntry h2 = new HandEntry(new Card("SQ"), 0);

        Assertions.assertEquals(-1, h1.compareTo(h2));
        Assertions.assertEquals(1, h2.compareTo(h1));
    }

    @Test
    public void testHandEntriesComparison__EqualCardOrder() {
        HandEntry h1 = new HandEntry(new Card("HA"), 1);
        HandEntry h2 = new HandEntry(new Card("SA"), 0);

        Assertions.assertEquals(-1, h1.compareTo(h2));
        Assertions.assertEquals(1, h2.compareTo(h1));
    }

    @Test
    public void testHandEntriesComparison__DifferentCardOrderAndPriorities() {
        HandEntry h1 = new HandEntry(new Card("H2"), 1);
        HandEntry h2 = new HandEntry(new Card("SA"), 0);

        Assertions.assertEquals(-1, h1.compareTo(h2));
        Assertions.assertEquals(1, h2.compareTo(h1));
    }
}
