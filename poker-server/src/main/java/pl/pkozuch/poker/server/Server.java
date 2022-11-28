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

/**
 * Hello world!
 */
public class Server {
    protected final Map<Integer, PlayerWrapper> players = new HashMap<>();
    protected final Map<Integer, Game> games = new HashMap<>();
    private Integer counter = 0;


    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    private void run() {
        ServerSocketChannel ssc = null;
        try (Selector selector = Selector.open()) {
            ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress("localhost", 4444));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            Queue<Integer> welcomeQueue = new PriorityQueue<>();

            while (ssc.isOpen()) {

                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        handleAccept(ssc, selector, welcomeQueue);
                    }

                    if (key.attachment() != null) {
                        handleWelcome(welcomeQueue, key);
                    }

                    if (key.isReadable()) {
                        handleRead(key);
                    }
                    iter.remove();
                }
            }
        } catch (IOException | NoSuchPlayerException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ssc != null)
                    ssc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleWelcome(Queue<Integer> welcomeQueue, SelectionKey key) throws NoSuchPlayerException {
        PlayerWrapper playerWrapper = (PlayerWrapper) key.attachment();
        Integer playerID = playerWrapper.getPlayerID();
        if (welcomeQueue.contains(playerID) && key.isWritable() && sendMessageToPlayer(playerID, playerID.toString())) {
            sendWelcomeMessage(welcomeQueue, playerWrapper, playerID);
        }
    }

    private void handleRead(SelectionKey key) throws NoSuchPlayerException {
        PlayerWrapper playerWrapper = (PlayerWrapper) key.attachment();

        if (!playerWrapper.isPlayerInGame()) {
            processAction(playerWrapper);
        }
    }

    private void handleAccept(ServerSocketChannel ssc, Selector selector, Queue<Integer> welcomeQueue) throws IOException {
        SocketChannel client = ssc.accept();

        PlayerWrapper wrapper = new PlayerWrapper(new ChannelController(selector, client));
        players.put(wrapper.getPlayerID(), wrapper);

        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, wrapper);

        welcomeQueue.add(wrapper.getPlayerID());
    }

    private void sendWelcomeMessage(Queue<Integer> welcomeQueue, PlayerWrapper playerWrapper, Integer playerID) throws NoSuchPlayerException {
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

    private void processAction(PlayerWrapper playerWrapper) throws NoSuchPlayerException {
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

    public Integer createGame(Integer ante) {
        Game newGame = new Game(++counter, ante);

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