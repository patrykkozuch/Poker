package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.actions.ActionFactory;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.actions.NoSuchActionException;
import pl.pkozuch.poker.common.Deck;
import pl.pkozuch.poker.common.Hand;
import pl.pkozuch.poker.common.HandSeniority;

import java.util.*;

public class GameController {
    private final Game game;
    private final ActionFactory actionFactory = new ActionFactory(this);
    private final MessageController messageController;

    private final Deck deck = new Deck();

    private Integer currentRoundBetPerPlayer;
    private possibleRoundStates roundState;
    private Integer reward;

    GameController(Game game) {
        this.game = game;
        messageController = new MessageController(this, game.getAllPlayers());
    }

    public void startGame() {
        sendMessageToAllPlayers("Rozpoczynanie gry...");

        currentRoundBetPerPlayer = 0;
        reward = 0;

        deck.buildDeck();

        for (Player p : game.getAllPlayers()) {
            p.resetStatus();
            p.cards = deck.draw(5);
        }

        roundState = possibleRoundStates.BETTING;

        sendMessageToAllPlayers("Gra rozpoczęta.");
    }

    public void startNextRound() {
        sendMessageToAllPlayers("\n\n\n");

        messageController.showBalanceToAllPlayers();
        messageController.showCardsSummaryToAllPlayers();

        switch (roundState) {
            case BETTING, SECOND_BETTING -> startBetting();
            case CHANGING -> startChanging();
            case END -> endRound();
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
        ArrayList<Player> activePlayers = getActivePlayers();

        while (activePlayers.size() != 0 && countPlayersInGame() != 1) {
            for (Player p : activePlayers) {

                if (countPlayersInGame() == 1)
                    return;

                sendMessageToAllPlayers("Ruch gracza: " + p.getId());

                boolean actionMade = false;

                while (!actionMade) {
                    try {
                        String action = askPlayerForAction(p);

                        actionFactory.create(p, action).make();

                        actionMade = true;
                    } catch (NoSuchActionException|IllegalArgumentException|IllegalActionException e) {
                        p.sendMessage(e.getMessage());
                    }
                }
            }
            activePlayers = getActivePlayers();
        }
    }

    public ArrayList<Player> getActivePlayers() {
        if (roundState == possibleRoundStates.BETTING || roundState == possibleRoundStates.SECOND_BETTING)
            return new ArrayList<>(
                    game.getAllPlayers().stream()
                            .filter(
                                    p -> ((currentRoundBetPerPlayer == 0 && !p.doesCheck()) || !Objects.equals(p.getBetInCurrentRound(), currentRoundBetPerPlayer)) && !p.doesFold() && !p.doesBetAllIn())
                            .toList());
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

    private void endRound() {
        ArrayList<Hand> hands = new ArrayList<>();

        for (Player p : game.getAllPlayers().stream().filter(p -> !p.doesFold()).toList()) {
            hands.add(new PlayerHand(Arrays.asList(p.cards), p).check());
            p.resetStatus();
        }

        rewardPlayers(hands);

        messageController.showBalanceToAllPlayers();
    }

    private void rewardPlayers(ArrayList<Hand> hands) {
        hands.sort(Hand::compareTo);

        Map<Integer, Integer> pools = calculatePools(game.getAllPlayers());

        int player_idx = 0, draws_count, j;
        Player p;
        do {
            p = ((PlayerHand) hands.get(player_idx)).getPlayer();

            j = player_idx + 1;
            draws_count = 1;
            while (j + 1 < hands.size() && hands.get(player_idx).compareTo(hands.get(j)) == 0) {
                draws_count++;
                j++;
            }

            Integer winnerID = p.getId();
            Integer prize = (p.doesBetAllIn() ? pools.get(p.getBetInCurrentGame()) : pools.get(0)) / draws_count;
            HandSeniority winningHand = hands.get(player_idx).getSeniority();

            sendMessageToAllPlayers("ZWYCIĘZCY:");
            sendMessageToAllPlayers(String.format("\t* Player %1d, wygrywa %2d z układem %3s!", winnerID, prize, winningHand));

            p.raiseBalance(prize);
        } while (p.doesBetAllIn() || draws_count != 1);
    }

    private Map<Integer, Integer> calculatePools(ArrayList<Player> players) {
        Map<Integer, Integer> pools = new HashMap<>();

        Integer currentBet = 0;

        ArrayList<Player> playersSortedByBetInGame = new ArrayList<>(players.stream().sorted(Comparator.comparing(Player::getBetInCurrentGame)).toList());

        for (int i = 0; i < players.size(); i++) {
            Player p = playersSortedByBetInGame.get(i);

            if (p.doesBetAllIn()) {
                pools.put(p.getBetInCurrentGame(), pools.getOrDefault(p.getBetInCurrentGame(), 0) + (p.getBetInCurrentGame() - currentBet) * (players.size() - i - 1));
            }

            pools.put(0, pools.getOrDefault(0, 0) + p.getBetInCurrentGame());

            currentBet = p.getBetInCurrentGame();
        }

        return pools;
    }

    public void sendMessageToAllPlayers(String message) {
        sendMessageToAllPlayersWithout(null, message);
    }

    public void sendMessageToAllPlayersWithout(Player without, String message) {
        for (Player p : game.getAllPlayers()) {
            if (p != without)
                p.sendMessage(message);
        }
    }

    //Functions used by Actions
    public boolean hasPlayerWithID(Integer playerID) {
        return game.hasPlayerWithID(playerID);
    }

    public boolean doesSomeoneBetThisRound() {
        return currentRoundBetPerPlayer != 0;
    }

    public Integer getCurrentRoundBetPerPlayer() {
        return currentRoundBetPerPlayer;
    }

    public synchronized void setCurrentRoundBetPerPlayer(Integer currentRoundBetPerPlayer) {
        this.currentRoundBetPerPlayer = currentRoundBetPerPlayer;
    }

    public synchronized void addToReward(Integer amount) {
        reward += amount;
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
        BETTING, CHANGING, SECOND_BETTING, END
    }


}
