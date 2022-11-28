package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.actions.IllegalActionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Wrapper responsible for all meta-actions connected with game - creating, joining/quitting players, setting host etc.
 */
public class Game {

    private static Integer counter = 0;
    private final Integer gameID;

    /**
     * Ante - value to be bet before game starts
     */
    private final Integer ante;

    private final HashMap<Integer, Player> players = new HashMap<>();

    private GameThread gameThread = null;

    private Integer hostID = null;

    /**
     * @param ante -
     */
    public Game(Integer ante) {
        this.gameID = ++counter;
        this.ante = ante;
    }

    public Integer getGameID() {
        return gameID;
    }

    /**
     * Adds player to game. If there were no players before addition, added player becomes a host.
     *
     * @param newPlayer - player to be added
     * @throws IllegalActionException if server is full
     */
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

    /**
     * Removes player from game.
     * <ul>
     *     <li>If player was the host and lobby is not empty - first player on the list becomes the host</li>
     *     <li>Otherwise hostID is set to {@code null}</li>
     * </ul>
     *
     * @param playerToRemoveID ID of a player which should be removed
     * @throws NoSuchPlayerException If there is no player with specified {@code playerToRemoveID}
     */
    public void removePlayer(Integer playerToRemoveID) throws NoSuchPlayerException {

        if (!players.containsKey(playerToRemoveID))
            throw new NoSuchPlayerException("Nie istnieje gracz o ID = " + playerToRemoveID);

        for (Player playerInLobby : players.values()) {
            if (playerToRemoveID != playerInLobby.getId()) {
                playerInLobby.sendMessage("Gracz Player " + playerToRemoveID + " opuścił lobby.");
                playerInLobby.sendMessage(getPlayersListAsString());
            } else
                playerInLobby.sendMessage("Opuściłeś lobby.");
        }

        players.remove(playerToRemoveID);
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(players.values());
    }

    public Integer getPlayersCount() {
        return players.size();
    }

    /**
     * Gets list of players in lobby as a {@code String}, ready to be shown to Player. Contains additional info.
     *
     * @return List of players
     */
    private synchronized String getPlayersListAsString() {
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

    /**
     * Tries to start the game. If succeeded, new gameThread is created.
     *
     * @throws IllegalActionException if game has already started
     */
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

    public void setHostID(Integer id) {
        hostID = id;
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

    /**
     * Gets Player by specified ID.
     *
     * @param playerID ID of a player to be found
     * @return Player object
     * @throws NoSuchPlayerException if there is no player with given ID in this game
     */
    public Player getPlayerByID(Integer playerID) throws NoSuchPlayerException {
        if (!players.containsKey(playerID))
            throw new NoSuchPlayerException("Nie udało się znaleźć gracza o podanym ID = " + playerID);

        return players.get(playerID);
    }
}
