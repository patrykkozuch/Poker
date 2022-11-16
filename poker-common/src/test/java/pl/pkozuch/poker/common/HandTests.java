package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class HandTests {


    @Test
    public void testHandCreatingAndSorting__AllInOneSuit() {

        final ArrayList<Card> cardsInOneSuitNotSorted = new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("HA"),
                new Card("H7"),
                new Card("HQ"),
                new Card("HT")
        ));

        final ArrayList<Card> cardsInOneSuitsSorted = new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("HQ"),
                new Card("HT"),
                new Card("H7"),
                new Card("H2")
        ));

        Hand handFromNotSortedCards = new Hand(cardsInOneSuitNotSorted);
        Hand handFromSortedCards = new Hand(cardsInOneSuitsSorted);

        Assertions.assertEquals(cardsInOneSuitsSorted, handFromNotSortedCards.getALlCards());
        Assertions.assertEquals(handFromNotSortedCards, handFromSortedCards);

    }

    @Test
    public void testHandCreatingAndSorting__DifferentSuits() {

        final ArrayList<Card> cardsInAllSuitsNotSorted = new ArrayList<>(Arrays.asList(
                new Card("D2"),
                new Card("HA"),
                new Card("C7"),
                new Card("SQ"),
                new Card("HT")
        ));

        final ArrayList<Card> cardsInAllSuitsSorted = new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("SQ"),
                new Card("HT"),
                new Card("C7"),
                new Card("D2")
        ));

        Hand handFromNotSortedCards = new Hand(cardsInAllSuitsNotSorted);
        Hand handFromSortedCards = new Hand(cardsInAllSuitsSorted);

        Assertions.assertEquals(cardsInAllSuitsSorted, handFromNotSortedCards.getALlCards());
        Assertions.assertEquals(handFromNotSortedCards, handFromSortedCards);
    }

    @Test
    public void testGetCardsOneByOne() {
        final ArrayList<Card> cardsInAllSuitsSorted = new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("SQ"),
                new Card("HT"),
                new Card("C7"),
                new Card("D2")
        ));

        Hand handFromSortedCards = new Hand(cardsInAllSuitsSorted);

        for (int i = 0; i < 5; i++)
            Assertions.assertEquals(cardsInAllSuitsSorted.get(i), handFromSortedCards.getCard(i));
    }

    @Test
    public void testGetAllCards() {
        final ArrayList<Card> cardsInAllSuitsSorted = new ArrayList<>(Arrays.asList(
                new Card("HA"),
                new Card("SQ"),
                new Card("HT"),
                new Card("C7"),
                new Card("D2")
        ));

        Hand handFromSortedCards = new Hand(cardsInAllSuitsSorted);

        Assertions.assertEquals(cardsInAllSuitsSorted, handFromSortedCards.getALlCards());

        ArrayList<Card> cardsFromHand = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            cardsFromHand.add(handFromSortedCards.getCard(i));

        Assertions.assertEquals(cardsFromHand, handFromSortedCards.getALlCards());
    }

    @Test
    public void handComparisonTest__sameSeniority() {
        //HIGH CARD
        final ArrayList<Card> higherPairCards = new ArrayList<>(Arrays.asList(
                new Card("H4"),
                new Card("S5"),
                new Card("H6"),
                new Card("S3"),
                new Card("C3")
        ));

        //HIGH CARD
        final ArrayList<Card> lowerPairCards = new ArrayList<>(Arrays.asList(
                new Card("HK"),
                new Card("CA"),
                new Card("SQ"),
                new Card("D2"),
                new Card("H2")
        ));

        final ArrayList<Card> equalToHigherPairCards = new ArrayList<>(Arrays.asList(
                new Card("D4"),
                new Card("C6"),
                new Card("H5"),
                new Card("D3"),
                new Card("H3")
        ));

        Hand higherPairHand = new Hand(higherPairCards);
        Hand lowerPairHand = new Hand(lowerPairCards);
        Hand equalToHigherPairHand = new Hand(equalToHigherPairCards);

        higherPairHand.check();
        lowerPairHand.check();
        equalToHigherPairHand.check();

        // -1, because higherHand because hand order is descending in comparison to seniority
        Assertions.assertEquals(-1, higherPairHand.compareTo(lowerPairHand));
        Assertions.assertEquals(0, higherPairHand.compareTo(equalToHigherPairHand));

        Assertions.assertEquals(1, lowerPairHand.compareTo(higherPairHand));
    }

    @Test
    public void handComparisonTest__differentSeniority() {
        Hand olderHand = new Hand(SampleHands.hands.get(HandSeniority.ROYAL_FLUSH));
        Hand youngerHand = new Hand(SampleHands.hands.get(HandSeniority.FULL_HOUSE));

        //Not an object of tests
        olderHand.check();
        youngerHand.check();

        Assertions.assertEquals(olderHand.compareTo(youngerHand), -1);
        Assertions.assertEquals(youngerHand.compareTo(olderHand), 1);
    }

    @Test
    public void testCheck() {
        for (HandSeniority seniority : HandSeniority.values()) {
            Hand h = new Hand(SampleHands.hands.get(seniority));
            h.check();

            Assertions.assertEquals(
                    seniority,
                    h.getSeniority(),
                    "Checks for seniority " + seniority + "has failed"
            );
        }
    }

    @Test
    public void testPriorities__determinedPriorities() {
        ArrayList<Card> sortedCards = new ArrayList<>(Arrays.asList(
                new Card("CK"),
                new Card("D9"),
                new Card("S7"),
                new Card("H3"),
                new Card("H2")
        ));

        ArrayList<Card> sortedCardsAfterPriorities = new ArrayList<>(Arrays.asList(
                new Card("S7"),
                new Card("D9"),
                new Card("H3"),
                new Card("CK"),
                new Card("H2")
        ));

        Hand h = new Hand(sortedCards);
        h.setPriority(new int[]{2, 4, 5, 3, 1});

        Assertions.assertEquals(sortedCardsAfterPriorities, h.getALlCards());
    }

    @Test
    public void testPriorities__afterCheck() {
        ArrayList<Card> fourOfAKind__beforeCheck = new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("D2"),
                new Card("HK"),
                new Card("S2"),
                new Card("C2")
        ));

        ArrayList<Card> fourOfAKind__afterCheck = new ArrayList<>(Arrays.asList(
                new Card("H2"),
                new Card("D2"),
                new Card("S2"),
                new Card("C2"),
                new Card("HK")
        ));

        Hand h = new Hand(fourOfAKind__beforeCheck);
        h.check();

        Assertions.assertEquals(fourOfAKind__afterCheck, h.getALlCards());
    }
}
