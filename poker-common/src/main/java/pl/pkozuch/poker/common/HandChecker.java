package pl.pkozuch.poker.common;

public class HandChecker {

    private HandChecker() {
    }

    private static boolean isRoyalFlush(Hand hand) {
        return hand.getCard(0).value == CardValues.ACE && isStraightFlush(hand);
    }

    private static boolean isStraightFlush(Hand hand) {
        return isFlush(hand) && isStraight(hand);
    }

    private static boolean isFlush(Hand hand) {
        return hand.getCard(0).suit == hand.getCard(1).suit
                && hand.getCard(1).suit == hand.getCard(2).suit
                && hand.getCard(2).suit == hand.getCard(3).suit
                && hand.getCard(3).suit == hand.getCard(4).suit;
    }

    private static boolean isStraight(Hand hand) {
        return hand.getCard(0).isBeforeInSeniorityOrder(hand.getCard(1))
                && hand.getCard(1).isBeforeInSeniorityOrder(hand.getCard(2))
                && hand.getCard(2).isBeforeInSeniorityOrder(hand.getCard(3))
                && hand.getCard(3).isBeforeInSeniorityOrder(hand.getCard(4));
    }

    private static boolean isFourOfAKind(Hand hand) {
        if (isFourOfAKind(hand.getCard(0), hand.getCard(1), hand.getCard(2), hand.getCard(3))) {
            hand.setPriority(new int[]{1, 1, 1, 1, 0});
            return true;
        }

        if (isFourOfAKind(hand.getCard(1), hand.getCard(2), hand.getCard(3), hand.getCard(4))) {
            hand.setPriority(new int[]{0, 1, 1, 1, 1});
            return true;
        }

        return false;
    }

    private static boolean isFullHouse(Hand hand) {
        if (isPair(hand.getCard(0), hand.getCard(1)) && isThreeOfAKind(hand.getCard(2), hand.getCard(3), hand.getCard(4))) {
            hand.setPriority(new int[]{0, 0, 1, 1, 1});
            return true;
        }


        if (isThreeOfAKind(hand.getCard(0), hand.getCard(1), hand.getCard(2)) && isPair(hand.getCard(3), hand.getCard(4))) {
            hand.setPriority(new int[]{1, 1, 1, 0, 0});
            return true;
        }

        return false;
    }

    private static boolean isThreeOfAKind(Hand hand) {
        if (isThreeOfAKind(hand.getCard(0), hand.getCard(1), hand.getCard(2))) {
            hand.setPriority(new int[]{1, 1, 1, 0, 0});
            return true;
        }

        if (isThreeOfAKind(hand.getCard(1), hand.getCard(2), hand.getCard(3))) {
            hand.setPriority(new int[]{0, 1, 1, 1, 0});
            return true;
        }

        if (isThreeOfAKind(hand.getCard(2), hand.getCard(3), hand.getCard(4))) {
            hand.setPriority(new int[]{0, 0, 1, 1, 1});
            return true;
        }

        return false;
    }

    private static boolean isThreeOfAKind(Card c1, Card c2, Card c3) {
        return isPair(c1, c2) && isPair(c2, c3);
    }

    private static boolean isFourOfAKind(Card c1, Card c2, Card c3, Card c4) {
        return isThreeOfAKind(c1, c2, c3) && isPair(c3, c4);
    }

    private static boolean isTwoPair(Hand hand) {
        if (isPair(hand.getCard(0), hand.getCard(1)) && isPair(hand.getCard(2), hand.getCard(3))) {
            hand.setPriority(new int[]{2, 2, 1, 1, 0});
            return true;
        }

        if (isPair(hand.getCard(0), hand.getCard(1)) && isPair(hand.getCard(3), hand.getCard(4))) {
            hand.setPriority(new int[]{2, 2, 0, 1, 1});
            return true;
        }

        if (isPair(hand.getCard(1), hand.getCard(2)) && isPair(hand.getCard(3), hand.getCard(4))) {
            hand.setPriority(new int[]{0, 2, 2, 1, 1});
            return true;
        }

        return false;
    }

    private static boolean isOnePair(Hand hand) {
        if (isPair(hand.getCard(0), hand.getCard(1))) {
            hand.setPriority(new int[]{1, 1, 0, 0, 0});
            return true;
        }

        if (isPair(hand.getCard(1), hand.getCard(2))) {
            hand.setPriority(new int[]{0, 1, 1, 0, 0});
            return true;
        }

        if (isPair(hand.getCard(2), hand.getCard(3))) {
            hand.setPriority(new int[]{0, 0, 1, 1, 0});
            return true;
        }

        if (isPair(hand.getCard(3), hand.getCard(4))) {
            hand.setPriority(new int[]{0, 0, 0, 1, 1});
            return true;
        }

        return false;
    }

    private static boolean isPair(Card c1, Card c2) {
        return c1.value == c2.value;
    }

    public static HandSeniority check(Hand hand) {

        if (isRoyalFlush(hand))
            return HandSeniority.ROYAL_FLUSH;

        if (isStraightFlush(hand))
            return HandSeniority.STRAIGHT_FLUSH;

        if (isFourOfAKind(hand))
            return HandSeniority.FOUR_OF_A_KIND;

        if (isFullHouse(hand))
            return HandSeniority.FULL_HOUSE;

        if (isFlush(hand))
            return HandSeniority.FLUSH;

        if (isStraight(hand))
            return HandSeniority.STRAIGHT;

        if (isThreeOfAKind(hand))
            return HandSeniority.THREE_OF_KIND;

        if (isTwoPair(hand))
            return HandSeniority.TWO_PAIR;

        if (isOnePair(hand))
            return HandSeniority.ONE_PAIR;

        return HandSeniority.HIGH_CARD;
    }
}
