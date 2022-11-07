package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HandCheckerTests {
    @Test
    public void testCheck() {
        for (HandSeniority seniority : HandSeniority.values())
            Assertions.assertEquals(
                    seniority,
                    HandChecker.check(new Hand(SampleHands.hands.get(seniority))),
                    "Checks for seniority " + seniority + " has failed."
            );
    }
}
