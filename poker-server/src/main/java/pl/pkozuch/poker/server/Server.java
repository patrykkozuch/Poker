package pl.pkozuch.poker.server;

import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.serveractions.ServerActionFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!
 */
public class Server {
    private Integer gameCounter = 1;
    private final Map<Integer, Game> games = new ConcurrentHashMap<>();
    private final Map<Integer, PlayerWrapper> players = new ConcurrentHashMap<>();

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
        try (Selector selector = Selector.open()) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress("localhost", 4444));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {

                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();

                    Queue<Integer> welcomeQueue = new PriorityQueue<>();
                    if (key.isAcceptable()) {
                        SocketChannel client = ssc.accept();

                        PlayerWrapper wrapper = new PlayerWrapper(selector, client);
                        players.put(wrapper.getPlayerID(), wrapper);

                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, wrapper);

                        welcomeQueue.add(wrapper.getPlayerID());
                    }

                    if (key.attachment() != null) {
                        Integer playerID = ((PlayerWrapper) key.attachment()).getPlayerID();
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (welcomeQueue.contains(playerID) && key.isWritable()) {
                            if (sendMessageToPlayer(playerID, playerID.toString())) {
                                String message = """
                                        Witaj na serwerze Pokera.
                                        Wybierz co chcesz zrobić:
                                        1. Stwórz nową grę
                                        2. Dołącz do istniejącej gry""";

                                sendMessageToPlayer(playerID, message);
                                welcomeQueue.remove(playerID);
                            }
                        }
                    }

                    if (key.isReadable()) {

                        PlayerWrapper playerWrapper = (PlayerWrapper) key.attachment();

                        if (!playerWrapper.isPlayerInGame()) {
                            try {
                                String message = readFromPlayer(playerWrapper.getPlayerID());
                                ServerActionFactory serverActionFactory = new ServerActionFactory(this);

                                serverActionFactory.create(playerWrapper, message).make();

                            } catch (Exception e) {
                                sendMessageToPlayer(playerWrapper.getPlayerID(), e.getMessage());
                            }
                        }
                    }

                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMessageToPlayer(Integer playerID, String message) {
        if (!hasPlayerWithID(playerID))
            throw new RuntimeException("Gracz o ID " + playerID + " nie istnieje.");

        return players.get(playerID).sendMessageToPlayer(message);
    }

    public String readFromPlayer(Integer playerID) {
        if (!hasPlayerWithID(playerID))
            throw new RuntimeException("Gracz o ID " + playerID + " nie istnieje.");

        return players.get(playerID).getResponseFromPlayer();
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

    public Collection<Game> getAllGames() {
        return games.values();
    }
}