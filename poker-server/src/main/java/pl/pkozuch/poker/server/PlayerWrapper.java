package pl.pkozuch.poker.server;

import pl.pkozuch.poker.logic.ChannelController;
import pl.pkozuch.poker.logic.Player;

public class PlayerWrapper {
    private final Player player;
    private Integer gameID = null;

    protected PlayerWrapper(ChannelController channelController) {
        player = new Player(channelController);
    }

    public Player getPlayer() {
        return player;
    }

    int getPlayerID() {
        return player.getId();
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public boolean sendMessageToPlayer(String message) {

        return player.sendMessage(message);
    }

    public String readFromPlayer() {
        return player.readFrom();
    }

    public boolean isPlayerInGame() {
        return player.isInGame();
    }
}
