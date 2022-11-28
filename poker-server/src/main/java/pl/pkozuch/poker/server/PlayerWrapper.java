package pl.pkozuch.poker.server;

import pl.pkozuch.poker.logic.ChannelController;
import pl.pkozuch.poker.logic.Player;

/**
 * Wrapper of Player class, contains gameID
 */
public class PlayerWrapper {
    protected Player player;
    private Integer gameID = null;

    protected PlayerWrapper() {
        player = null;
    }

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

    /**
     * {@link Player#sendMessage(String)}
     *
     * @param message message to be sent
     * @return true if message was sent successfully, false otherwise
     */
    public boolean sendMessageToPlayer(String message) {
        return player.sendMessage(message);
    }

    /**
     * Reads message from player
     *
     * @return {@code String} message, if failed returns {@code null}
     */
    public String readFromPlayer() {
        return player.readFrom();
    }

    public boolean isPlayerInGame() {
        return player.isInGame();
    }
}
