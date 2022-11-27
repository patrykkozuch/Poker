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

    public void resetStatus() {
        doesFold = false;
        doesBetAllIn = false;
        doesCheck = false;
        hasChangedCards = false;
        resetBet();
    }

    public boolean sendMessage(String message) {
        return channelController.writeToChannel(message);
    }

    public String readFrom() {
        return channelController.readFromChannel();
    }

    public void changeCard(Integer idx, Card c) {
        this.cards[idx] = c;
    }

    public Card[] getCards() {
        return this.cards.clone();
    }
}
