package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.common.Card;

import java.util.Random;

public class Player {
    private static int counter;
    private final int id;
    private final ChannelController channelController;
    protected Card[] cards = new Card[5];
    private boolean inGame = false;
    private Integer balance;
    private boolean doesBetAllIn = false;
    private boolean doesFold = false;
    private boolean doesCheck = false;
    private boolean hasChangedCards = false;
    private Integer betInCurrentRound = 0;
    private Integer betInCurrentGame = 0;

    /**
     * Creates player. Player receives ID based on how many players was created so far.
     * Players are by default created with random balance.
     *
     * @param channelController which controls writing and reading from Player channel
     */
    public Player(ChannelController channelController) {
        id = ++counter;

        this.channelController = channelController;

        Random random = new Random();
        this.balance = random.nextInt(1000);
    }

    public int getId() {
        return id;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean v) {
        inGame = v;
    }

    public void raiseBalance(Integer reward) {
        balance += reward;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void reduceBalance(Integer amount) {
        this.balance -= amount;
    }

    public void betAllIn() {
        this.doesBetAllIn = true;
    }

    public boolean doesBetAllIn() {
        return doesBetAllIn;
    }

    public void fold() {
        this.doesFold = true;
    }

    public boolean doesFold() {
        return doesFold;
    }

    public void check() {
        this.doesCheck = true;
    }

    public boolean doesCheck() {
        return doesCheck;
    }

    public void changeCards() {
        this.hasChangedCards = true;
    }

    public boolean hasChangedCards() {
        return hasChangedCards;
    }

    /**
     * Raises bet in current round and game
     *
     * @param amount to be raised
     */
    public void raiseBetInCurrentRound(Integer amount) {
        betInCurrentRound += amount;
        betInCurrentGame += amount;
    }

    public Integer getBetInCurrentRound() {
        return betInCurrentRound;
    }

    public Integer getBetInCurrentGame() {
        return betInCurrentGame;
    }

    public void resetBet() {
        betInCurrentRound = 0;
    }

    /**
     * Reset status of fold, all-in bet, check, card change and bet overall
     */
    public void resetStatus() {
        doesFold = false;
        doesBetAllIn = false;
        doesCheck = false;
        hasChangedCards = false;
        resetBet();
    }

    /**
     * Send a message to Player with use of {@link ChannelController#writeToChannel(String)}
     *
     * @param message message to be sent
     * @return {@code true} if message was sent successfully, {@code false} otherwise
     */
    public boolean sendMessage(String message) {
        return channelController.writeToChannel(message);
    }

    /**
     * Reads a message from Player with use of {@link ChannelController#readFromChannel()}
     *
     * @return message received from Player, {@code null} otherwise
     */
    public String readFrom() {
        return channelController.readFromChannel();
    }

    /**
     * Changes card with id = {@code idx} to card {@code c}
     *
     * @param idx idx of card to be changed
     * @param c   new Card
     */
    public void changeCard(Integer idx, Card c) {
        this.cards[idx] = c;
    }

    /**
     * @return copy of cards
     */
    public Card[] getCards() {
        return this.cards.clone();
    }
}
