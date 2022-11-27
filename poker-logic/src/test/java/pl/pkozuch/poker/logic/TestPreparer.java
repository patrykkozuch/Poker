package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.actions.PlayerStub;

public class TestPreparer {

    public static Object[] createGameControllerAndPlayers() {
        return createGameControllerAndPlayers(0);
    }
    
    /**
     * @return Object[GameController, Player, Player, Player]
     */
    public static Object[] createGameControllerAndPlayers(Integer ante) {

        Game game = new Game(1, ante);

        GameController gameController = new GameController(game);

        Player p1 = new PlayerStub();
        Player p2 = new PlayerStub();
        Player p3 = new PlayerStub();

        p1.setBalance(100);
        p2.setBalance(100);
        p3.setBalance(100);

        try {
            game.addPlayer(p1);
            game.addPlayer(p2);
            game.addPlayer(p3);
        } catch (IllegalActionException e) {
            e.printStackTrace();
        }

        gameController.startGame();

        return new Object[]{gameController, p1, p2, p3};
    }
}
