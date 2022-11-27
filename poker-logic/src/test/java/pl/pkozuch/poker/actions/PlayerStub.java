package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.Player;

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

    void setAction(String action) {
        this.action = this.getId() + " " + action;
    }
}
