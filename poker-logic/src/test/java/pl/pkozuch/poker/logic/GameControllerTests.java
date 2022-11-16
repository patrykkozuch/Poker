package pl.pkozuch.poker.logic;

//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
public class GameControllerTests {
//    @Test
//    public void testGameStart() {
//        GameController gc = new GameController(new StreamController(System.in, System.out));
//
//        gc.startGame();
//
//        Assertions.assertEquals(0, gc.getGameCounter());
//        Assertions.assertEquals(0, gc.getRoundCounter());
//        Assertions.assertEquals(0, gc.getReward());
//        Assertions.assertEquals(0, gc.getCurrentRoundBetPerPlayer());
//        Assertions.assertEquals(GameController.possibleRoundStates.BETTING, gc.getRoundState());
//
//        Assertions.assertEquals(52, gc.getDeck().count());
//    }
//
//    @Test
//    public void testAddPlayers() {
//        GameController gc = new GameController(new StreamController(System.in, System.out));
//
//        Player p1 = new Player(new StreamController(System.in, System.out));
//        Player p2 = new Player(new StreamController(System.in, System.out));
//        Player p3 = new Player(new StreamController(System.in, System.out));
//
//        gc.addPlayer(p1);
//        gc.addPlayer(p2);
//        gc.addPlayer(p3);
//
//        ArrayList<Player> players = gc.getAllPlayers();
//        ArrayList<Player> activePlayers = gc.getActivePlayers();
//
//        Assertions.assertEquals(players, activePlayers);
//        Assertions.assertEquals(3, players.size());
//
//        Assertions.assertTrue(players.contains(p1));
//        Assertions.assertTrue(players.contains(p2));
//        Assertions.assertTrue(players.contains(p3));
//
//        gc.removePlayer(p3);
//
//        Assertions.assertEquals(2, gc.getAllPlayers().size());
//    }
//
//    @Test
//    public void testReward() {
//        GameController gc = new GameController(new StreamController(System.in, System.out));
//
//        gc.startGame();
//
//        Assertions.assertEquals(0, gc.getReward());
//
//        gc.addToReward(20);
//
//        Assertions.assertEquals(20, gc.getReward());
//
//        gc.addToReward(30);
//
//        Assertions.assertEquals(50, gc.getReward());
//    }
}
