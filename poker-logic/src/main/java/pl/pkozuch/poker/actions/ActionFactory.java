package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

import java.util.Arrays;

public class ActionFactory {
    private final GameController gameController;

    private String actionSlug;
    private String[] actionsArgs;

    public ActionFactory(GameController gameController) {
        this.gameController = gameController;
    }

    public Action create(Player player, String message) {
        parseAction(message);

        //TODO: Add ALLIN
        return switch (actionSlug) {
            case "FOLD" -> new FoldAction(gameController, player);
            case "RAISE" -> new RaiseAction(gameController, player, actionsArgs);
            case "CALL" -> new CallAction(gameController, player);
            case "CHECK" -> new CheckAction(gameController, player);
            case "CHANGE" -> new ChangeAction(gameController, player, actionsArgs);
            case "ALLIN" -> new AllInAction(gameController, player);
            default -> throw new RuntimeException("Nieprawidłowa akcja. Spróbuj ponownie");
        };
    }

    private void parseAction(String enteredAction) {
        String[] splitAction = enteredAction.trim().split(" ");

        if (splitAction.length < 2)
            throw new RuntimeException("Nie wprowadzono wymaganej liczby argumentów. Minimum to 3: <ID_GRACZA> <ID_GRY=0> <AKCJA>");

        if (!IntValidator.isInt(splitAction[0]))
            throw new RuntimeException("Pierwszym parametrem powinien być identyfikator gracza będący liczbą całkowitą");

        Integer playerID = Integer.parseInt(splitAction[0]);

        if (!gameController.hasPlayerWithID(playerID))
            throw new RuntimeException("Gracz o ID " + playerID + " nie istnieje.");

        actionSlug = splitAction[1].toUpperCase();

        if (splitAction.length == 2) {
            actionsArgs = null;
        } else {
            actionsArgs = Arrays.copyOfRange(splitAction, 2, splitAction.length);
        }
    }
}
