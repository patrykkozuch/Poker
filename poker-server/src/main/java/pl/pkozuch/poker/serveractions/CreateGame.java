package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class CreateGame extends ServerAction {

    private final Integer ante;

    CreateGame(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args == null || args.length != 1)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");

        if (!IntValidator.isInt(args[0]))
            throw new IllegalArgumentException("Ante powinno być liczbą całkowitą.");

        ante = Integer.parseInt(args[0]);
    }

    public static String getHelpString() {
        return "CREATE <ante>";
    }

    @Override
    public void validate() throws IllegalActionException {
        if (playerWrapper.getGameID() != null)
            throw new IllegalActionException("Jesteś już członkiem gry. Aby stworzyć nową grę, najpierw opuść aktualną (QUIT).");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        try {
            Integer gameID = server.createGame(ante);
            playerWrapper.sendMessageToPlayer("Udało się utworzyć nową grę. ID gry: " + gameID);
        } catch (Exception e) {
            playerWrapper.sendMessageToPlayer("Nie udało się utworzyć nowej gry. " + e.getMessage());
        }
    }
}
