package pl.pkozuch.poker.logic;

public class GameThread extends Thread {
    private final GameController gameController;
    private final Game game;

    GameThread(Game game) {
        gameController = new GameController(game);
        this.game = game;
    }

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
    }
}
