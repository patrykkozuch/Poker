package pl.pkozuch.poker.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SampleHands {
    public static final Map<HandSeniority, ArrayList<Card>> hands = new HashMap<>() {{

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

    public static final Map<ArrayList<Card>, HandSeniority> allPossibleHands = new HashMap<>() {{
        hands.forEach((key, value) -> put(value, key));

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("DA"),
                new Card("CA"),
                new Card("H2"),
                new Card("C2")
        )), HandSeniority.FULL_HOUSE);


        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("CA"),
                new Card("DA"),
                new Card("SQ"),
                new Card("HT")
        )), HandSeniority.THREE_OF_KIND);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("HK"),
                new Card("DK"),
                new Card("CK"),
                new Card("HT")
        )), HandSeniority.THREE_OF_KIND);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("CA"),
                new Card("DK"),
                new Card("CK"),
                new Card("HT")
        )), HandSeniority.TWO_PAIR);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("CA"),
                new Card("H2"),
                new Card("D2"),
                new Card("HT")
        )), HandSeniority.TWO_PAIR);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("DA"),
                new Card("H2"),
                new Card("SQ"),
                new Card("HT")
        )), HandSeniority.ONE_PAIR);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("DK"),
                new Card("HK"),
                new Card("SQ"),
                new Card("HT")
        )), HandSeniority.ONE_PAIR);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("DK"),
                new Card("CQ"),
                new Card("HQ"),
                new Card("HT")
        )), HandSeniority.ONE_PAIR);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("HK"),
                new Card("HQ"),
                new Card("CJ"),
                new Card("HT")
        )), HandSeniority.STRAIGHT);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("HK"),
                new Card("HQ"),
                new Card("HJ"),
                new Card("CT")
        )), HandSeniority.STRAIGHT);

        put(new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("HK"),
                new Card("HJ"),
                new Card("HT"),
                new Card("H9")
        )), HandSeniority.FLUSH);
    }};
}
