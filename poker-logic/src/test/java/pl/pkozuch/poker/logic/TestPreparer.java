package pl.pkozuch.poker.logic;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class TestPreparer {
    public static Object[] createGameControllerAndPlayers() {
        return createGameControllerAndPlayers("");
    }

    /**
     * @return Object[GameController, Player, Player, Player]
     */
    public static Object[] createGameControllerAndPlayers(String actions) {
        if (!actions.equals("")) {
            System.setIn(new ByteArrayInputStream(actions.getBytes(StandardCharsets.UTF_8)));
        }

        GameController gameController = new GameController(null, new StreamController(System.in, System.out));

        Player p1 = new Player(new StreamController(System.in, System.out));
        Player p2 = new Player(new StreamController(System.in, System.out));
        Player p3 = new Player(new StreamController(System.in, System.out));

        p1.setBalance(100);
        p2.setBalance(100);
        p3.setBalance(100);

//        gameController.addPlayer(p1);
//        gameController.addPlayer(p2);
//        gameController.addPlayer(p3);

        gameController.startGame();

        return new Object[]{gameController, p1, p2, p3};
    }
}
