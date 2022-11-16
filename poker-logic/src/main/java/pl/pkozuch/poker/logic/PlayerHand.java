package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.common.Card;
import pl.pkozuch.poker.common.Hand;

import java.util.List;

public class PlayerHand extends Hand {

    private final Player player;

    PlayerHand(List<Card> cards, Player player) {
        super(cards);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
