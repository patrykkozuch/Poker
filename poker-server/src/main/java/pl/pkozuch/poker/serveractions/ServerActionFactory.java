package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.server.Server;
import pl.pkozuch.poker.server.ServerThread;

import java.util.Arrays;

public class ServerActionFactory {
    private final Server server;

    private Integer playerID;
    private String actionSlug;
    private String[] actionsArgs;

    public ServerActionFactory(Server server) {
        this.server = server;
    }

    public ServerAction create(ServerThread playerThread, String enteredAction) {
        parseAction(enteredAction);

        return switch (actionSlug) {
            case "CREATE" -> new CreateGame(server, playerThread, actionsArgs);
            case "JOIN" -> new JoinGame(server, playerThread, actionsArgs);
            case "START" -> new StartGame(server, playerThread, actionsArgs);
            case "QUIT" -> new QuitGame(server, playerThread, actionsArgs);
            case "LIST" -> new ListGames(server, playerThread, actionsArgs);
            case "BALANCE" -> new Balance(server, playerThread, actionsArgs);
            case "HELP" -> new Help(server, playerThread, actionsArgs);
            default -> throw new RuntimeException("Wybrana akcja nie istnieje.");
        };
    }

    private void parseAction(String enteredAction) {
        String[] splitAction = enteredAction.trim().split(" ");

        if (splitAction.length < 2)
            throw new RuntimeException("Nie wprowadzono wymaganej liczby argumentów. Minimum to 3: <ID_GRACZA> <ID_GRY=0> <AKCJA>");

        if (!IntValidator.isInt(splitAction[0]))
            throw new RuntimeException("Pierwszym parametrem powinien być identyfikator gracza będący liczbą całkowitą");

        playerID = Integer.parseInt(splitAction[0]);

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
