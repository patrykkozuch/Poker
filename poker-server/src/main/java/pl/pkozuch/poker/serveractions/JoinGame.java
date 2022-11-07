package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.Server;
import pl.pkozuch.poker.server.ServerThread;

public class JoinGame extends ServerAction {

    private final Integer gameID;

    JoinGame(Server server, ServerThread playerThread, String[] args) {
        super(server, playerThread);

        if (args == null || args.length != 1)
            throw new RuntimeException("Nieprawidłowa liczba argumentów" + args[0] + args[1]);

        if (!IntValidator.isInt(args[0]))
            throw new RuntimeException("Identyfikator gry powinien być liczbą całkowitą");

        gameID = Integer.parseInt(args[0]);


    }

    @Override
    public void validate() {
        if (!server.hasGameWithID(gameID))
            throw new RuntimeException("Nie istnieje gra o podanym ID");

        if (playerThread.getGameID() != null)
            throw new RuntimeException("Jesteś już członkiem gry. Aby dołączyć do innej gry, najpierw opuść aktualną (QUIT).");

        if (playerThread.getPlayer().getBalance() < server.getGame(gameID).getAnte())
            throw new RuntimeException("Nie możesz dołączyć do gry, ponieważ nie masz wystarczającej ilości pieniędzy do wprowadzenia ante."
                    + "Wymagane to "
                    + server.getGame(gameID).getAnte()
                    + ", a twój stan konta wynosi "
                    + playerThread.getPlayer().getBalance());
    }

    @Override
    public void make() {
        super.make();

        try {
            Game g = server.getGame(gameID);
            g.addPlayer(playerThread.getPlayer());
            playerThread.setGameID(gameID);
            playerThread.getPlayer().reduceBalance(g.getAnte());
        } catch (Exception e) {
            playerThread.sendMessageToPlayer("Nie udało się dołączyć do gry. " + e.getMessage());
        }
    }


    public static String getHelpString() {
        return "JOIN <id_gry>";
    }
}
