package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.actions.ActionFactory;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.actions.NoSuchActionException;
import pl.pkozuch.poker.common.Deck;
import pl.pkozuch.poker.common.Hand;
import pl.pkozuch.poker.common.HandSeniority;

import java.util.*;

/**
 * Class responsible for poker logic
 */
public class GameController {
    private final Game game;
    private final ActionFactory actionFactory = new ActionFactory(this);
    private final MessageController messageController;

    private final Deck deck = new Deck();

    private Integer currentRoundBetPerPlayer;
    private possibleRoundStates roundState = possibleRoundStates.START;

    /**
     * Creates gameController.
     *
     * @param game game for which controller is being created
     */
    public GameController(Game game) {
        this.game = game;
        messageController = new MessageController(this);
    }

    /**
     * Starts game and reset game state.
     */
    public void startGame() {
        sendMessageToAllPlayers("Rozpoczynanie gry...");

        currentRoundBetPerPlayer = 0;

        deck.buildDeck();

        for (Player p : game.getAllPlayers()) {
            p.resetStatus();
            p.cards = deck.draw(5);
        }

        roundState = possibleRoundStates.BETTING;

        sendMessageToAllPlayers("Gra rozpoczęta.");
    }

    /**
     * Starts new round
     */
    public void startNextRound() {
        sendMessageToAllPlayers("\n\n\n");

        messageController.showBalanceToAllPlayers();
        messageController.showCardsSummaryToAllPlayers();

        switch (roundState) {
            case BETTING, SECOND_BETTING -> startBetting();
            case CHANGING -> startChanging();
            case END -> endRound();
            default -> throw new GameNotStartedException("Gra jeszcze się nie rozpoczęła.");
        }
    }

    private void startBetting() {
        currentRoundBetPerPlayer = 0;

        game.getAllPlayers().forEach(Player::resetBet);

        playersActions();

        updateRoundState();
    }

    private void startChanging() {
        playersActions();

        updateRoundState();
    }

    private void playersActions() {
        List<Player> activePlayers = getActivePlayers();

        while (!activePlayers.isEmpty() && countPlayersInGame() != 1) {
            for (Player p : activePlayers) {

                if (countPlayersInGame() == 1)
                    return;

                sendMessageToAllPlayers("Ruch gracza: " + p.getId());

                processPlayerAction(p);
            }
            activePlayers = getActivePlayers();
        }
    }

    private void processPlayerAction(Player p) {
        boolean actionMade = false;

        while (!actionMade) {
            try {
                String action = askPlayerForAction(p);

                actionFactory.create(p, action).make();

                actionMade = true;
            } catch (NoSuchActionException | IllegalArgumentException | IllegalActionException e) {
                p.sendMessage(e.getMessage());
            } catch (NoSuchPlayerException e) {
                //Will never happen
            }
        }
    }

    /**
     * Gets list of active players.
     * <p>
     * 'Active' Player means that:
     * <ul>
     *     <li>During betting phase: Player does not fold or bet All-In,
     *     does not bet the same amount than others or nobody bet and player still do not check</li>
     *     <li>During changing phase: Player does not fold and does not changed their cards</li>
     *     <li>During end phase: Player does not fold</li>
     * </ul>
     *
     * @return list of active players
     */
    private List<Player> getActivePlayers() {
        if (roundState == possibleRoundStates.BETTING || roundState == possibleRoundStates.SECOND_BETTING)
            return new ArrayList<>(
                    game.getAllPlayers().stream()
                            .filter(
                                    p -> ((currentRoundBetPerPlayer == 0 && !p.doesCheck()) || !Objects.equals(p.getBetInCurrentRound(), currentRoundBetPerPlayer)) && !p.doesFold() && !p.doesBetAllIn())
                            .toList());
        else if (roundState == possibleRoundStates.END)
            return new ArrayList<>(
                    game.getAllPlayers().stream()
                            .filter(
                                    p -> !p.doesFold()
                            ).toList()
            );
        else
            return new ArrayList<>(
                    game.getAllPlayers().stream()
                            .filter(p -> !p.hasChangedCards() && !p.doesFold())
                            .toList());
    }

    private int countPlayersInGame() {
        long playersInGameCounter = game.getAllPlayers().stream().filter(player -> !player.doesFold()).count();
        return (int) playersInGameCounter;
    }

    /**
     * Gets action string from Player {@code p}
     *
     * @param p Players to be asked for action
     * @return actionString provided by Player
     */
    private String askPlayerForAction(Player p) {
        if (roundState == possibleRoundStates.BETTING || roundState == possibleRoundStates.SECOND_BETTING) {
            messageController.showBetMessageToPlayer(p);
        } else {
            messageController.showChangeMessageForPlayer(p);
        }

        String response;
        do {
            response = messageController.getPlayerResponse(p);
        } while (response == null);
        return response;
    }

    private void updateRoundState() {
        roundState = possibleRoundStates.values()[roundState.ordinal() + 1 % possibleRoundStates.values().length];
    }

    /**
     * Ends round and rewards Players
     */
    private void endRound() {
        ArrayList<Hand> hands = new ArrayList<>();

        for (Player p : game.getAllPlayers().stream().filter(p -> !p.doesFold()).toList()) {
            hands.add(new PlayerHand(Arrays.asList(p.cards), p).check());
        }

        rewardPlayers(hands);

        messageController.showBalanceToAllPlayers();
        game.getAllPlayers().forEach(Player::resetStatus);
    }

    private void rewardPlayers(ArrayList<Hand> hands) {
        hands.sort(Hand::compareTo);

        Map<Integer, Integer> pools = calculatePools(game.getAllPlayers());

        int playerIdx = -1;
        int drawsCount;
        int j;
        Player p;
        do {
            p = ((PlayerHand) hands.get(++playerIdx)).getPlayer();

            j = 0;
            drawsCount = 1;
            while (j < hands.size() && hands.get(playerIdx).compareTo(hands.get(j)) == 0) {
                if (j != playerIdx) {
                    drawsCount++;
                }
                j++;
            }

            Integer winnerID = p.getId();
            Integer prize = pools.get(p.getBetInCurrentGame()) / drawsCount;
            HandSeniority winningHand = hands.get(playerIdx).getSeniority();

            sendMessageToAllPlayers("ZWYCIĘZCY:");
            sendMessageToAllPlayers(String.format("\t* Player %1d, wygrywa %2d z układem %3s!", winnerID, prize, winningHand));

            p.raiseBalance(prize);
        } while (p.doesBetAllIn() || (playerIdx + 1 < getActivePlayersCount() && hands.get(playerIdx).compareTo(hands.get(playerIdx + 1)) == 0));
    }

    private int getActivePlayersCount() {
        return getActivePlayers().size();
    }

    private Map<Integer, Integer> calculatePools(List<Player> players) {
        Map<Integer, Integer> pools = new HashMap<>();

        Integer currentBet = 0;

        ArrayList<Player> playersSortedByBetInGame = new ArrayList<>(players.stream().sorted(Comparator.comparing(Player::getBetInCurrentGame)).toList());

        for (int i = 0; i < players.size(); i++) {
            Player p = playersSortedByBetInGame.get(i);

            pools.put(p.getBetInCurrentGame(), pools.getOrDefault(p.getBetInCurrentGame(), 0) + (p.getBetInCurrentGame() - currentBet) * (players.size() - i));

            currentBet = p.getBetInCurrentGame();
        }

        return pools;
    }

    /**
     * Sends message to all players in lobby
     *
     * @param message message to be sent
     */
    public void sendMessageToAllPlayers(String message) {
        sendMessageToAllPlayersWithout(null, message);
    }

    /**
     * Sends message to all player, except the one specified
     *
     * @param without Player who should not receive the message
     * @param message message to be sent
     */
    public void sendMessageToAllPlayersWithout(Player without, String message) {
        for (Player p : game.getAllPlayers()) {
            if (p != without)
                p.sendMessage(message);
        }
    }

    /**
     * Checks if player is active.
     * <p>
     * Being active is explained in: {@link GameController#getActivePlayers()}
     *
     * @param playerID ID of a player to be checked
     * @return true if player is active, false otherwise
     * @throws NoSuchPlayerException if there is no Player with specified ID in this game
     */
    public boolean isPlayerActive(Integer playerID) throws NoSuchPlayerException {
        return getActivePlayers().contains(game.getPlayerByID(playerID));
    }

    //Functions used by Actions

    public boolean hasPlayerWithID(Integer playerID) {
        return game.hasPlayerWithID(playerID);
    }

    public boolean doesSomeoneBetThisRound() {
        return currentRoundBetPerPlayer != 0;
    }

    public synchronized int getCurrentRoundBetPerPlayer() {
        return currentRoundBetPerPlayer;
    }

    public synchronized void setCurrentRoundBetPerPlayer(Integer currentRoundBetPerPlayer) {
        this.currentRoundBetPerPlayer = currentRoundBetPerPlayer;
    }

    public possibleRoundStates getRoundState() {
        return roundState;
    }

    public Deck getDeck() {
        return deck;
    }

    public Game getGame() {
        return game;
    }

    public enum possibleRoundStates {
        START, BETTING, CHANGING, SECOND_BETTING, END
    }


}
