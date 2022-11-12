package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class JoinGame extends ServerAction {

    private final Integer gameID;

    JoinGame(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args == null || args.length != 1)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");

        if (!IntValidator.isInt(args[0]))
            throw new IllegalArgumentException("Identyfikator gry powinien być liczbą całkowitą");

        gameID = Integer.parseInt(args[0]);
    }

    public static String getHelpString() {
        return "JOIN <id_gry>";
    }

    @Override
    public void validate() throws IllegalActionException {
        if (!server.hasGameWithID(gameID))
            throw new IllegalActionException("Nie istnieje gra o podanym ID");

        if (playerWrapper.getGameID() != null)
            throw new IllegalActionException("Jesteś już członkiem gry. Aby dołączyć do innej gry, najpierw opuść aktualną (QUIT).");

        if (playerWrapper.getPlayer().getBalance() < server.getGame(gameID).getAnte())
            throw new IllegalActionException("Nie możesz dołączyć do gry, ponieważ nie masz wystarczającej ilości pieniędzy do wprowadzenia ante."
                    + "Wymagane to "
                    + server.getGame(gameID).getAnte()
                    + ", a twój stan konta wynosi "
                    + playerWrapper.getPlayer().getBalance());
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        try {
            Game g = server.getGame(gameID);
            g.addPlayer(playerWrapper.getPlayer());
            playerWrapper.setGameID(gameID);
            playerWrapper.getPlayer().reduceBalance(g.getAnte());
        } catch (Exception e) {
            playerWrapper.sendMessageToPlayer("Nie udało się dołączyć do gry. " + e.getMessage());
        }
    }
}
