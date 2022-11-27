package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.NoSuchActionException;
import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.NoSuchPlayerException;
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

    public ServerAction create(PlayerWrapper playerWrapper, String enteredAction) throws NoSuchPlayerException, NoSuchActionException, IllegalArgumentException {
        parseAction(enteredAction);

        return switch (actionSlug) {
            case "CREATE" -> new CreateGameAction(server, playerWrapper, actionsArgs);
            case "JOIN" -> new JoinGameAction(server, playerWrapper, actionsArgs);
            case "START" -> new StartGameAction(server, playerWrapper, actionsArgs);
            case "QUIT" -> new QuitGameAction(server, playerWrapper, actionsArgs);
            case "LIST" -> new ListGamesAction(server, playerWrapper, actionsArgs);
            case "BALANCE" -> new BalanceAction(server, playerWrapper, actionsArgs);
            case "HELP" -> new HelpAction(server, playerWrapper, actionsArgs);
            default -> throw new NoSuchActionException("Wybrana akcja nie istnieje.");
        };
    }

    private void parseAction(String enteredAction) throws NoSuchPlayerException, IllegalArgumentException {
        String[] splitAction = enteredAction.trim().split(" ");

        if (splitAction.length < 2)
            throw new IllegalArgumentException("Nie wprowadzono wymaganej liczby argumentów. Minimum to 3: <ID_GRACZA> <ID_GRY=0> <AKCJA>");

        if (!IntValidator.isInt(splitAction[0]))
            throw new IllegalArgumentException("Pierwszym parametrem powinien być identyfikator gracza będący liczbą całkowitą");

        Integer playerID = Integer.parseInt(splitAction[0]);

        if (server.doesNotHavePlayer(playerID))
            throw new NoSuchPlayerException("Gracz o ID " + playerID + " nie istnieje.");

        actionSlug = splitAction[1].toUpperCase();

        if (splitAction.length == 2) {
            actionsArgs = null;
        } else {
            actionsArgs = Arrays.copyOfRange(splitAction, 2, splitAction.length);
        }
    }
}
