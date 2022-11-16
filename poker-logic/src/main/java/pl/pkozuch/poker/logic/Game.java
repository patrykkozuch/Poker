package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.actions.IllegalActionException;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private final HashMap<Integer, Player> players = new HashMap<>();
    final Integer gameID;
    GameThread gameThread = null;
    final Integer ante;
    private Integer hostID = null;

    public Game(Integer gameID, Integer ante) {
        this.gameID = gameID;
        this.ante = ante;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void addPlayer(Player newPlayer) throws IllegalActionException {
        if (players.size() >= 4)
            throw new IllegalActionException("Serwer gry jest pełny (4/4). Nie można dołączyć.");

        if (players.size() == 0)
            hostID = newPlayer.getId();

        players.put(newPlayer.getId(), newPlayer);

        for (Player playerInLobby : players.values()) {
            if (newPlayer != playerInLobby)
                playerInLobby.sendMessage("Gracz Player " + newPlayer.getId() + " dołączył do lobby.");
            else
                playerInLobby.sendMessage("Dołączyłeś do lobby.");
        }
    }

    public void removePlayer(Integer playerToRemoveID) {

        for (Player playerInLobby : players.values()) {
            if (playerToRemoveID != playerInLobby.getId()) {
                playerInLobby.sendMessage("Gracz Player " + playerToRemoveID + " opuścił lobby.");
                playerInLobby.sendMessage(getPlayersListAsString());
            } else
                playerInLobby.sendMessage("Opuściłeś lobby.");
        }

        players.remove(playerToRemoveID);
    }

    public ArrayList<Player> getAllPlayers() {
        return new ArrayList<>(players.values());
    }

    public Integer getPlayersCount() {
        return players.size();
    }

    synchronized private String getPlayersListAsString() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("Aktualnie w lobby: \n");
        for (Player p : players.values()) {
            stringBuffer.append("\t* Player ").append(p.getId());
            if (p.getId() == hostID)
                stringBuffer.append(" (HOST)");
            stringBuffer.append("\n");
        }

        return stringBuffer.toString();
    }

    public void startGame() throws IllegalActionException {
        if (gameThread == null || !gameThread.isAlive()) {
            gameThread = new GameThread(this);
            gameThread.start();

        } else throw new IllegalActionException("Nie udało się rozpocząć gry. Gra już się rozpoczęła.");
    }

    //Functions used by Actions/ServerActions
    public Integer getHostID() {
        return hostID;
    }

    public boolean hasPlayerWithID(Integer playerID) {
        return players.containsKey(playerID);
    }

    public Integer getAnte() {
        return ante;
    }

    public String getStatus() {
        if (gameThread == null) {
            return "Nierozpoczęta";
        } else if (!gameThread.isAlive()) {
            return "Zakończona";
        } else {
            return "W trakcie";
        }
    }
}
