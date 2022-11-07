package pl.pkozuch.poker.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SampleHands {
    public static Map<HandSeniority, ArrayList<Card>> hands = new HashMap<>() {{

        put(HandSeniority.ROYAL_FLUSH, new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("HK"),
                new Card("HQ"),
                new Card("HJ"),
                new Card("HT")
        )));

        put(HandSeniority.STRAIGHT_FLUSH, new ArrayList<>(Arrays.asList(
                new Card("HK"),
                new Card("HQ"),
                new Card("HJ"),
                new Card("HT"),
                new Card("H9")
        )));

        put(HandSeniority.FOUR_OF_A_KIND, new ArrayList<>(Arrays.asList(
                new Card("HK"),
                new Card("DK"),
                new Card("SK"),
                new Card("CK"),
                new Card("HT")
        )));

        put(HandSeniority.FULL_HOUSE, new ArrayList<>(Arrays.asList(
                new Card("HK"),
                new Card("DK"),
                new Card("SK"),
                new Card("HA"),
                new Card("DA")
        )));

        put(HandSeniority.FLUSH, new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("H4"),
                new Card("H6"),
                new Card("H8"),
                new Card("HT")
        )));

        put(HandSeniority.STRAIGHT, new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("D3"),
                new Card("S4"),
                new Card("H5"),
                new Card("C6")
        )));

        put(HandSeniority.THREE_OF_KIND, new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("D2"),
                new Card("C2"),
                new Card("H5"),
                new Card("ST")
        )));

        put(HandSeniority.TWO_PAIR, new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("D2"),
                new Card("C5"),
                new Card("S5"),
                new Card("HT")
        )));

        put(HandSeniority.ONE_PAIR, new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("D2"),
                new Card("H7"),
                new Card("CQ"),
                new Card("HT")
        )));

        put(HandSeniority.HIGH_CARD, new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("DA"),
                new Card("C7"),
                new Card("SQ"),
                new Card("HT")
        )));
    }};
}
