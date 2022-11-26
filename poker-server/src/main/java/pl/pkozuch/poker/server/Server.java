package pl.pkozuch.poker.server;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.actions.NoSuchActionException;
import pl.pkozuch.poker.logic.ChannelController;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.logic.NoSuchPlayerException;
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
    private final Map<Integer, Game> games = new ConcurrentHashMap<>();
    private final Map<Integer, PlayerWrapper> players = new ConcurrentHashMap<>();
    private Integer gameCounter = 1;

    Server() {
        try (Selector selector = Selector.open()) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress("localhost", 4444));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            Queue<Integer> welcomeQueue = new PriorityQueue<>();
            while (true) {

                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        SocketChannel client = ssc.accept();

                        PlayerWrapper wrapper = new PlayerWrapper(new ChannelController(selector, client));
                        players.put(wrapper.getPlayerID(), wrapper);

                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, wrapper);

                        welcomeQueue.add(wrapper.getPlayerID());
                    }

                    if (key.attachment() != null) {
                        PlayerWrapper playerWrapper = (PlayerWrapper) key.attachment();
                        Integer playerID = playerWrapper.getPlayerID();
                        if (welcomeQueue.contains(playerID) && key.isWritable()) {
                            if (sendMessageToPlayer(playerID, playerID.toString())) {
                                String message = """
                                        Witaj na serwerze Pokera.
                                        Wybierz co chcesz zrobić:
                                        """;

                                sendMessageToPlayer(playerID, message);

                                try {
                                    ServerActionFactory actionFactory = new ServerActionFactory(this);
                                    actionFactory.create(playerWrapper, playerID + " HELP").make();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                welcomeQueue.remove(playerID);
                            }
                        }
                    }

                    if (key.isReadable()) {

                        PlayerWrapper playerWrapper = (PlayerWrapper) key.attachment();

                        if (!playerWrapper.isPlayerInGame()) {
                            try {
                                String message = readFromPlayer(playerWrapper.getPlayerID());
                                if (message != null) {
                                    ServerActionFactory serverActionFactory = new ServerActionFactory(this);
                                    serverActionFactory.create(playerWrapper, message).make();
                                }
                            } catch (NoSuchActionException | IllegalActionException |
                                    NoSuchPlayerException e) {
                                sendMessageToPlayer(playerWrapper.getPlayerID(), e.getMessage());
                            } catch (IllegalArgumentException e) {
                                sendMessageToPlayer(playerWrapper.getPlayerID(), e.getMessage() + " Sprawdź prawidłowe użycie akcji poleceniem HELP");
                            }
                        }
                    }
                    iter.remove();
                }
            }
        } catch (IOException | NoSuchPlayerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }

    public Integer createGame(Integer ante) {
        Game newGame = new Game(gameCounter++, ante);

        games.put(newGame.getGameID(), newGame);

        return newGame.getGameID();
    }

    public Game getGame(Integer gameID) {
        return games.get(gameID);
    }

    public boolean sendMessageToPlayer(Integer playerID, String message) throws NoSuchPlayerException {
        if (doesNotHavePlayer(playerID))
            throw new NoSuchPlayerException("Gracz o ID " + playerID + " nie istnieje.");

        return players.get(playerID).sendMessageToPlayer(message);
    }

    public String readFromPlayer(Integer playerID) throws NoSuchPlayerException {
        if (doesNotHavePlayer(playerID))
            throw new NoSuchPlayerException("Gracz o ID " + playerID + " nie istnieje.");

        return players.get(playerID).readFromPlayer();
    }

    public boolean hasGameWithID(Integer gameID) {
        return gameID == 0 || games.containsKey(gameID);
    }

    public boolean doesNotHavePlayer(Integer playerID) {
        return !players.containsKey(playerID);
    }

    public Collection<Game> getAllGames() {
        return games.values();
    }
}