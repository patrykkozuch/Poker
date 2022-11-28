package pl.pkozuch.poker.logic;

/**
 * Thread in which game will be processed
 */
public class GameThread extends Thread {
    private final GameController gameController;
    private final Game game;

    /**
     * Creates thread and runs game
     *
     * @param game Game to be run
     */
    GameThread(Game game) {
        gameController = new GameController(game);
        this.game = game;
    }

    /**
     * Runs game
     */
    @Override
    public void run() {
        super.run();

        for (Player p : game.getAllPlayers()) {
            p.setInGame(true);
        }

        gameController.startGame();

        //Betting
        gameController.startNextRound();
        //Changing
        gameController.startNextRound();
        //SecondBetting
        gameController.startNextRound();
        //End
        gameController.startNextRound();

        for (Player p : game.getAllPlayers()) {
            p.setInGame(false);
        }

        gameController.sendMessageToAllPlayers("The game has ended.");

        try {
            for (Player p : game.getAllPlayers()) {
                game.removePlayer(p.getId());
            }
        } catch (NoSuchPlayerException e) {
            //Will never happen
        }
    }
}
