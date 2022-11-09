package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class CreateGame extends ServerAction {

    private final Integer ante;

    CreateGame(Server server, PlayerWrapper playerWrapper, String[] args) {
        super(server, playerWrapper);

        if (args == null || args.length != 1)
            throw new RuntimeException("Nieprawidłowa liczba argumentów");

        if (!IntValidator.isInt(args[0]))
            throw new RuntimeException("Ante powinno być liczbą całkowitą.");

        ante = Integer.parseInt(args[0]);
    }

    @Override
    public void validate() {
        if (playerWrapper.getGameID() != null)
            throw new RuntimeException("Jesteś już członkiem gry. Aby stworzyć nową grę, najpierw opuść aktualną (QUIT).");
    }

    @Override
    public void make() {
        super.make();

        try {
            Integer gameID = server.createGame(ante);
            playerWrapper.sendMessageToPlayer("Udało się utworzyć nową grę. ID gry: " + gameID);
        } catch (Exception e) {
            playerWrapper.sendMessageToPlayer("Nie udało się utworzyć nowej gry. " + e.getMessage());
        }
    }

    public static String getHelpString() {
        return "CREATE <ante>";
    }
}
