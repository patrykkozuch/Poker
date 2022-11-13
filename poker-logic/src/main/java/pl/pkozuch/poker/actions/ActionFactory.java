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

    public Action create(Player player, String message) throws NoSuchActionException {
        parseAction(message);

        return switch (actionSlug) {
            case "FOLD" -> new FoldAction(gameController, player);
            case "RAISE" -> new RaiseAction(gameController, player, actionsArgs);
            case "CALL" -> new CallAction(gameController, player);
            case "CHECK" -> new CheckAction(gameController, player);
            case "CHANGE" -> new ChangeAction(gameController, player, actionsArgs);
            case "ALLIN" -> new AllInAction(gameController, player);
            default -> throw new NoSuchActionException("Nieprawidłowa akcja. Spróbuj ponownie");
        };
    }

    private void parseAction(String enteredAction) throws IllegalArgumentException {
        String[] splitAction = enteredAction.trim().split(" ");

        if (splitAction.length < 2)
            throw new IllegalArgumentException("Nie wprowadzono akcji.");

        if (!IntValidator.isInt(splitAction[0]))
            throw new IllegalArgumentException("Pierwszym parametrem powinien być identyfikator gracza będący liczbą całkowitą");

        Integer playerID = Integer.parseInt(splitAction[0]);

        if (!gameController.hasPlayerWithID(playerID))
            throw new IllegalArgumentException("Gracz o ID " + playerID + " nie istnieje.");

        actionSlug = splitAction[1].toUpperCase();

        if (splitAction.length == 2) {
            actionsArgs = null;
        } else {
            actionsArgs = Arrays.copyOfRange(splitAction, 2, splitAction.length);
        }
    }
}
