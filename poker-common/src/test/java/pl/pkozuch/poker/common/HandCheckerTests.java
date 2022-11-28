package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

class HandCheckerTests {
    @Test
    void testCheck() {
        for (Map.Entry<ArrayList<Card>, HandSeniority> entry : SampleHands.allPossibleHands.entrySet())
            Assertions.assertEquals(
                    entry.getValue(),
                    HandChecker.check(new Hand(entry.getKey())),
                    "Checks for seniority " + entry.getValue() + " has failed. With cards" + entry.getKey()
            );
    }
}
