package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.NoSuchPlayerException;
import pl.pkozuch.poker.logic.Player;

import java.util.Arrays;

/**
 * Factory class which creates needed actions based on user input
 */
public class ActionFactory {
    /**
     * GameController on which Action will be performed
     */
    private final GameController gameController;

    /**
     * Action slug provided by Player
     */
    private String actionSlug;

    /**
     * Arguments provided by Player, if none set tu null
     */
    private String[] actionsArgs;

    public ActionFactory(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Creates Action based on Player input
     *
     * @param player  which wants to perform action
     * @param message Player input
     * @return Action if action was valid
     * @throws NoSuchActionException if there are no action with entered {@link ActionFactory#actionSlug}
     * @throws NoSuchPlayerException if there is no player with ID specified in {@code message}
     */
    public Action create(Player player, String message) throws NoSuchActionException, NoSuchPlayerException {
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

    /**
     * Parses action entered by Player
     *
     * @param enteredAction Player input
     * @throws IllegalArgumentException if arguments are not valid (in term of types, length and so on)
     * @throws NoSuchPlayerException    if there is no player with ID specified in {@code enteredAction}
     */
    private void parseAction(String enteredAction) throws IllegalArgumentException, NoSuchPlayerException {
        String[] splitAction = enteredAction.trim().split(" ");

        if (splitAction.length < 2)
            throw new IllegalArgumentException("Nie wprowadzono akcji.");

        if (!IntValidator.isInt(splitAction[0]))
            throw new IllegalArgumentException("Pierwszym parametrem powinien być identyfikator gracza będący liczbą całkowitą");

        Integer playerID = Integer.parseInt(splitAction[0]);

        if (!gameController.hasPlayerWithID(playerID))
            throw new NoSuchPlayerException("Gracz o ID " + playerID + " nie istnieje.");

        actionSlug = splitAction[1].toUpperCase();

        if (splitAction.length == 2) {
            actionsArgs = null;
        } else {
            actionsArgs = Arrays.copyOfRange(splitAction, 2, splitAction.length);
        }
    }
}
