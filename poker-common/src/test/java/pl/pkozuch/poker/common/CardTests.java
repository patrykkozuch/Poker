package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTests {

    @Test
    void testCardCreationFromString__CheckSuits() {
        for (CardSuits suit : CardSuits.values()) {
            Assertions.assertEquals(
                    new Card(suit.getShortcut() + "2").getSuit(),
                    suit,
                    "Couldn't create card with value: " + suit.getShortcut()
            );
        }
    }

    @Test
    void testCardCreationFromString__CheckValues() {
        for (CardValues value : CardValues.values()) {
            Assertions.assertEquals(
                    new Card("S" + value.getShortcut()).getValue(),
                    value,
                    "Couldn't create card with value: " + value
            );
        }
    }

    @Test
    void testIsCardBeforeAnotherInSeniorityOrder() {
        Card ace = new Card("HA");
        Card king = new Card("SK");

        Assertions.assertTrue(ace.isBeforeInSeniorityOrder(king));
        Assertions.assertFalse(king.isBeforeInSeniorityOrder(ace));
        Assertions.assertFalse(king.isBeforeInSeniorityOrder(king));
    }
}
