package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.common.Card;
import pl.pkozuch.poker.logic.Player;

import java.util.ArrayList;

public class PlayerStub extends Player {
    private String action = "";

    public PlayerStub() {
        super(null);
    }

    @Override
    public boolean sendMessage(String message) {
        return true;
    }

    @Override
    public String readFrom() {
        return action;
    }

    public void setAction(String action) {
        this.action = this.getId() + " " + action;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards.toArray(this.cards);
    }
}
