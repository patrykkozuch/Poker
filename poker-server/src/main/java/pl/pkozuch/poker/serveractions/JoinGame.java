package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class JoinGame extends ServerAction {

    private final Integer gameID;

    JoinGame(Server server, PlayerWrapper playerWrapper, String[] args) {
        super(server, playerWrapper);

        if (args == null || args.length != 1)
            throw new RuntimeException("Nieprawidłowa liczba argumentów");

        if (!IntValidator.isInt(args[0]))
            throw new RuntimeException("Identyfikator gry powinien być liczbą całkowitą");

        gameID = Integer.parseInt(args[0]);


    }

    @Override
    public void validate() {
        if (!server.hasGameWithID(gameID))
            throw new RuntimeException("Nie istnieje gra o podanym ID");

        if (playerWrapper.getGameID() != null)
            throw new RuntimeException("Jesteś już członkiem gry. Aby dołączyć do innej gry, najpierw opuść aktualną (QUIT).");

        if (playerWrapper.getPlayer().getBalance() < server.getGame(gameID).getAnte())
            throw new RuntimeException("Nie możesz dołączyć do gry, ponieważ nie masz wystarczającej ilości pieniędzy do wprowadzenia ante."
                    + "Wymagane to "
                    + server.getGame(gameID).getAnte()
                    + ", a twój stan konta wynosi "
                    + playerWrapper.getPlayer().getBalance());
    }

    @Override
    public void make() {
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


    public static String getHelpString() {
        return "JOIN <id_gry>";
    }
}
