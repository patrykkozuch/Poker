package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

import java.util.Arrays;

public class ServerActionFactory {
    private final Server server;

    private String actionSlug;
    private String[] actionsArgs;

    public ServerActionFactory(Server server) {
        this.server = server;
    }

    public ServerAction create(PlayerWrapper playerWrapper, String enteredAction) {
        parseAction(enteredAction);

        return switch (actionSlug) {
            case "CREATE" -> new CreateGame(server, playerWrapper, actionsArgs);
            case "JOIN" -> new JoinGame(server, playerWrapper, actionsArgs);
            case "START" -> new StartGame(server, playerWrapper, actionsArgs);
            case "QUIT" -> new QuitGame(server, playerWrapper, actionsArgs);
            case "LIST" -> new ListGames(server, playerWrapper, actionsArgs);
            case "BALANCE" -> new Balance(server, playerWrapper, actionsArgs);
            case "HELP" -> new Help(server, playerWrapper, actionsArgs);
            default -> throw new RuntimeException("Wybrana akcja nie istnieje.");
        };
    }

    private void parseAction(String enteredAction) {
        String[] splitAction = enteredAction.trim().split(" ");

        if (splitAction.length < 2)
            throw new RuntimeException("Nie wprowadzono wymaganej liczby argumentów. Minimum to 3: <ID_GRACZA> <ID_GRY=0> <AKCJA>");

        if (!IntValidator.isInt(splitAction[0]))
            throw new RuntimeException("Pierwszym parametrem powinien być identyfikator gracza będący liczbą całkowitą");

        Integer playerID = Integer.parseInt(splitAction[0]);

        if (!server.hasPlayerWithID(playerID))
            throw new RuntimeException("Gracz o ID " + playerID + " nie istnieje.");

        actionSlug = splitAction[1].toUpperCase();

        if (splitAction.length == 2) {
            actionsArgs = null;
        } else {
            actionsArgs = Arrays.copyOfRange(splitAction, 2, splitAction.length);
        }
    }
}
