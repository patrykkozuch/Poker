package pl.pkozuch.poker.logic;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.actions.PlayerStub;

public class TestPreparer {
    /**
     * @return Object[GameController, Player, Player, Player]
     */
    public static Object[] createGameControllerAndPlayers() {

        Game game = new Game(1, 0);

        GameController gameController = new GameController(game);

        Player p1 = new PlayerStub();
        Player p2 = new PlayerStub();
        Player p3 = new PlayerStub();

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
