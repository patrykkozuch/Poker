package pl.pkozuch.poker.server;

import pl.pkozuch.poker.logic.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!
 */
public class Server {
    private static ServerSocket serverSocket;
    private static Socket socket;
    private Integer gameCounter = 1;
    private final Map<Integer, Game> games = new ConcurrentHashMap<>();
    private final Map<Integer, ServerThread> players = new ConcurrentHashMap<>();

    public Integer createGame(Integer ante) {
        Game newGame = new Game(gameCounter++, ante);

        games.put(newGame.getGameID(), newGame);

        return newGame.getGameID();
    }

    void endGame(Integer gameID) {
        games.remove(gameID);
    }

    public Game getGame(Integer gameID) {
        return games.get(gameID);
    }

    Server() {
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server error");

        }

        while (true) {
            try {
                socket = serverSocket.accept();

                System.out.println("connection Established");

                ServerThread st = new ServerThread(this, socket);
                st.start();

                players.put(st.getPlayerID(), st);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }

    public boolean hasGameWithID(Integer gameID) {
        return gameID == 0 || games.containsKey(gameID);
    }

    public boolean hasPlayerWithID(Integer playerID) {
        return players.containsKey(playerID);
    }

    public void sendMessageToPlayer(Integer playerID, String message) {
        if (!hasPlayerWithID(playerID))
            throw new RuntimeException("Gracz o ID " + playerID + " nie istnieje.");

        players.get(playerID).sendMessageToPlayer(message);
    }

    public Collection<Game> getAllGames() {
        return games.values();
    }
}