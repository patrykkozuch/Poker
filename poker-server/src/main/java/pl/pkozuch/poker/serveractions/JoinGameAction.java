package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

/**
 * Join Action allows Player to join selected game
 */
public class JoinGameAction extends ServerAction {

    @SuppressWarnings("unused")
    @UsedViaReflection
    public static final String HELP_STRING = "JOIN <id_gry>";
    private final Integer gameID;

    /**
     * @param server        {@link ServerAction#server}
     * @param playerWrapper {@link ServerAction#playerWrapper}
     * @param args          game number
     * @throws IllegalArgumentException if {@code args} does not contain only one element or element is not a number
     */
    JoinGameAction(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args == null || args.length != 1)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");

        if (!IntValidator.isInt(args[0]))
            throw new IllegalArgumentException("Identyfikator gry powinien być liczbą całkowitą");

        gameID = Integer.parseInt(args[0]);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalActionException if:
     *                                <ul>
     *                                    <li>Game of specified ID does not exist</li>
     *                                    <li>Player is a member of another game</li>
     *                                    <li>Player does not have amount enough to provide ante</li>
     *                                </ul>
     */
    @Override
    protected void validate() throws IllegalActionException {
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

        Game g = server.getGame(gameID);
        g.addPlayer(playerWrapper.getPlayer());
        playerWrapper.setGameID(gameID);
        playerWrapper.getPlayer().reduceBalance(g.getAnte());
    }
}
