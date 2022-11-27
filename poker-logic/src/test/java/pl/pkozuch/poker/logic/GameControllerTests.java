package pl.pkozuch.poker.logic;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkozuch.poker.actions.PlayerStub;
import pl.pkozuch.poker.common.HandSeniority;

public class GameControllerTests {
    @Test
    public void testGameStart() {
        Game game = new Game(0, 0);
        GameController gc = new GameController(game);

        gc.startGame();

        Assertions.assertEquals(0, gc.getCurrentRoundBetPerPlayer());
        Assertions.assertEquals(GameController.possibleRoundStates.BETTING, gc.getRoundState());

        Assertions.assertEquals(52, gc.getDeck().count());
    }

    @Test
    public void testGameFlow__EveryoneFolds() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("FOLD");
        p2.setAction("FOLD");
        p3.setAction("FOLD");

        gameController.startGame();

        gameController.startNextRound();

        gameController.startNextRound();

        gameController.startNextRound();

        gameController.startNextRound();

        Assertions.assertEquals(p1.getBalance(), 100);
        Assertions.assertEquals(p2.getBalance(), 100);
        Assertions.assertEquals(p3.getBalance(), 100);
    }

    @Test
    public void testGameFlow__EveryoneChecks() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("CHECK");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        gameController.startGame();

        gameController.startNextRound();

        p1.setAction("CHANGE 0");
        p2.setAction("CHANGE 0");
        p3.setAction("CHANGE 0");

        gameController.startNextRound();

        p1.setAction("CHECK");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        gameController.startNextRound();

        gameController.startNextRound();

        Assertions.assertEquals(p1.getBalance(), 100);
        Assertions.assertEquals(p2.getBalance(), 100);
        Assertions.assertEquals(p3.getBalance(), 100);
    }

    @Test
    public void testGameFlow__OnePlayerRaiseOtherFolds() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("FOLD");
        p2.setAction("RAISE 10");
        p3.setAction("FOLD");

        gameController.startGame();

        gameController.startNextRound();

        gameController.startNextRound();

        gameController.startNextRound();

        gameController.startNextRound();

        Assertions.assertEquals(p1.getBalance(), 100);
        Assertions.assertEquals(p2.getBalance(), 100);
        Assertions.assertEquals(p3.getBalance(), 100);
    }

    @Test
    public void testGameFlow__OnePlayerRaiseOtherCall() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("RAISE 10");
        p2.setAction("CALL");
        p3.setAction("CALL");

        gameController.startGame();

        p1.setCards(SampleHands.hands.get(HandSeniority.HIGH_CARD));
        p2.setCards(SampleHands.hands.get(HandSeniority.ROYAL_FLUSH));
        p3.setCards(SampleHands.hands.get(HandSeniority.HIGH_CARD));
        
        gameController.startNextRound();

        p1.setAction("CHANGE 0");
        p2.setAction("CHANGE 0");
        p3.setAction("CHANGE 0");

        gameController.startNextRound();

        p1.setAction("CHECK");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        gameController.startNextRound();

        gameController.startNextRound();

        Assertions.assertEquals(p1.getBalance(), 90);
        Assertions.assertEquals(p2.getBalance(), 120);
        Assertions.assertEquals(p3.getBalance(), 90);
    }

    @Test
    public void testGameFlow__Draw() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("RAISE 10");
        p2.setAction("CALL");
        p3.setAction("CALL");

        gameController.startGame();

        p1.setCards(SampleHands.hands.get(HandSeniority.ROYAL_FLUSH));
        p2.setCards(SampleHands.hands.get(HandSeniority.ROYAL_FLUSH));
        p3.setCards(SampleHands.hands.get(HandSeniority.HIGH_CARD));


        gameController.startNextRound();

        p1.setAction("CHANGE 0");
        p2.setAction("CHANGE 0");
        p3.setAction("CHANGE 0");

        gameController.startNextRound();

        p1.setAction("CHECK");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        gameController.startNextRound();

        gameController.startNextRound();

        Assertions.assertEquals(105, p1.getBalance());
        Assertions.assertEquals(105, p2.getBalance());
        Assertions.assertEquals(90, p3.getBalance());
    }

    @Test
    public void testGameFlow__AllIn() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setBalance(50);
        p2.setBalance(40);
        p3.setBalance(30);

        p1.setAction("RAISE 45");
        p2.setAction("ALLIN");
        p3.setAction("ALLIN");

        gameController.startGame();

        p1.setCards(SampleHands.hands.get(HandSeniority.HIGH_CARD));
        p2.setCards(SampleHands.hands.get(HandSeniority.TWO_PAIR));
        p3.setCards(SampleHands.hands.get(HandSeniority.ROYAL_FLUSH));

        gameController.startNextRound();

        p1.setAction("CHANGE 0");
        p2.setAction("CHANGE 0");
        p3.setAction("CHANGE 0");

        gameController.startNextRound();

        p1.setAction("CHECK");

        gameController.startNextRound();

        gameController.startNextRound();

        Assertions.assertEquals(10, p1.getBalance());
        Assertions.assertEquals(20, p2.getBalance());
        Assertions.assertEquals(90, p3.getBalance());
    }

    @Test
    void testRoundState() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("CHECK");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        gameController.startGame();

        Assertions.assertEquals(GameController.possibleRoundStates.BETTING, gameController.getRoundState());
        gameController.startNextRound();

        p1.setAction("CHANGE 0");
        p2.setAction("CHANGE 0");
        p3.setAction("CHANGE 0");

        Assertions.assertEquals(GameController.possibleRoundStates.CHANGING, gameController.getRoundState());
        gameController.startNextRound();

        p1.setAction("CHECK");
        p2.setAction("CHECK");
        p3.setAction("CHECK");

        Assertions.assertEquals(GameController.possibleRoundStates.SECOND_BETTING, gameController.getRoundState());
        gameController.startNextRound();

        Assertions.assertEquals(GameController.possibleRoundStates.END, gameController.getRoundState());
        gameController.startNextRound();
    }

    @Test
    void testIsUserActive() throws NoSuchPlayerException {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("FOLD");
        p2.setAction("RAISE 100");
        p3.setAction("ALLIN");

        gameController.startGame();

        Assertions.assertTrue(gameController.isPlayerActive(p1.getId()));
        gameController.startNextRound();
        Assertions.assertFalse(gameController.isPlayerActive(p1.getId()));

        p2.setAction("CHANGE 0");
        p3.setAction("CHANGE 0");

        Assertions.assertFalse(gameController.isPlayerActive(p1.getId()));
        gameController.startNextRound();

        p2.setAction("CHECK");

        Assertions.assertFalse(gameController.isPlayerActive(p1.getId()));
        Assertions.assertFalse(gameController.isPlayerActive(p3.getId()));
        gameController.startNextRound();

        Assertions.assertFalse(gameController.isPlayerActive(p1.getId()));
        Assertions.assertTrue(gameController.isPlayerActive(p3.getId()));
        gameController.startNextRound();
    }

    @Test
    void testRoundBet() {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        GameController gameController = (GameController) objects[0];

        PlayerStub p1 = (PlayerStub) objects[1];
        PlayerStub p2 = (PlayerStub) objects[2];
        PlayerStub p3 = (PlayerStub) objects[3];

        p1.setAction("RAISE 10");
        p2.setAction("CALL");
        p3.setAction("CALL");

        gameController.startGame();

        Assertions.assertFalse(gameController.doesSomeoneBetThisRound());

        gameController.startNextRound();

        Assertions.assertTrue(gameController.doesSomeoneBetThisRound());
        Assertions.assertEquals(10, gameController.getCurrentRoundBetPerPlayer());

        p1.setAction("CHANGE 0");
        p2.setAction("CHANGE 0");
        p3.setAction("CHANGE 0");

        gameController.startNextRound();

        p1.setAction("RAISE 30");
        p2.setAction("CALL");
        p3.setAction("CALL");

        gameController.startNextRound();

        Assertions.assertTrue(gameController.doesSomeoneBetThisRound());
        Assertions.assertEquals(30, gameController.getCurrentRoundBetPerPlayer());
    }

    @Test
    void testStartNextRound__BeforeGameStart() {
        Game game = new Game(0, 0);
        GameController gameController = new GameController(game);

        Assertions.assertThrows(GameNotStartedException.class, gameController::startNextRound);
    }
}