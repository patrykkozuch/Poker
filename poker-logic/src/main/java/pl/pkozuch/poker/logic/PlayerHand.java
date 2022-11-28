package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.common.Card;
import pl.pkozuch.poker.common.Hand;

import java.util.List;
import java.util.Objects;

/**
 * Wrapper for Hand class, contains information about user whom the hand belongs to
 */
public class PlayerHand extends Hand {

    private final Player player;

    /**
     * @param cards  List of cards on {@code player} hand
     * @param player Player whose hand is it
     */
    PlayerHand(List<Card> cards, Player player) {
        super(cards);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerHand that = (PlayerHand) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player);
    }
}
