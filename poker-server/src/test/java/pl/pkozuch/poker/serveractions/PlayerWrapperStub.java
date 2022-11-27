package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.server.PlayerWrapper;

public class PlayerWrapperStub extends PlayerWrapper {
    private String sentMessage = "";
    private String action = "";

    public PlayerWrapperStub() {
        super(null);
    }

    @Override
    public boolean sendMessageToPlayer(String message) {
        sentMessage = message;
        return true;
    }

    public String getSentMessage() {
        return sentMessage;
    }

    @Override
    public String readFromPlayer() {
        return action;
    }

    public void setAction(String action) {
        this.action = this.getPlayer().getId() + " " + action;
    }
}
