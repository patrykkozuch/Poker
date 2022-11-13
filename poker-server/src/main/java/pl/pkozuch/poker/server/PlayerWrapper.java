package pl.pkozuch.poker.server;

import pl.pkozuch.poker.logic.Player;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class PlayerWrapper {
    private final Player player;
    private Integer gameID = null;

    PlayerWrapper(Selector selector, SocketChannel channel) {
        player = new Player(selector, channel);
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

    public String getResponseFromPlayer() throws IOException {
        return player.getResponse();
    }

    public boolean isPlayerInGame() {
        return player.isInGame();
    }
}
