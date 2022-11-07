package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.common.Card;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Player {
    public static int counter;
    private final int id;

    Card[] cards = new Card[5];

    private Integer balance;

    private boolean doesBetAllIn = false;
    private boolean doesFold = false;
    private boolean doesCheck = false;
    private boolean hasChangedCards = false;

    private Integer betInCurrentRound = 0;
    private Integer betInCurrentGame = 0;

    private final StreamController streamController;
    private final ReentrantLock readLock = new ReentrantLock();

    public Player(StreamController streamController) {
        id = ++counter;
        this.streamController = streamController;
        Random random = new Random();
        this.balance = random.nextInt(1000);
    }

    public int getId() {
        return id;
    }

    public Card[] getCards() {
        return Arrays.copyOf(cards, 5);
    }

    public void raiseBalance(Integer reward) {
        balance += reward;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
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

    public void sendMessage(String message) {
        streamController.sendWithNewLine(message);
    }

    public String getResponse() {
        return streamController.readLine();
    }

    public void lockRead() {
        readLock.lock();
    }

    public void unlockRead() {
        readLock.unlock();
    }

    public boolean isLocked() {
        return readLock.isLocked();
    }

    public boolean isInReady() {
        return streamController.isInReady();
    }

    public void changeCard(Integer idx, Card c) {
        this.cards[idx] = c;
    }

}
