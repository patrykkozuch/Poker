package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

/**
 * Creates game action - creates Game on server with specified ANTE
 */
public class CreateGameAction extends ServerAction {

    @SuppressWarnings("unused")
    @UsedViaReflection
    public static final String HELP_STRING = "CREATE <ante>";
    private final Integer ante;

    /**
     * @param server        {@link ServerAction#server}
     * @param playerWrapper {@link ServerAction#playerWrapper}
     * @throws IllegalArgumentException if ante is not specified or is not an integer
     */
    CreateGameAction(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args == null || args.length != 1)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");

        if (!IntValidator.isInt(args[0]))
            throw new IllegalArgumentException("Ante powinno być liczbą całkowitą.");

        ante = Integer.parseInt(args[0]);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalActionException if Player is currently in game
     */
    @Override
    protected void validate() throws IllegalActionException {
        if (playerWrapper.getGameID() != null)
            throw new IllegalActionException("Jesteś już członkiem gry. Aby stworzyć nową grę, najpierw opuść aktualną (QUIT).");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        Integer gameID = server.createGame(ante);
        playerWrapper.sendMessageToPlayer("Udało się utworzyć nową grę. ID gry: " + gameID);
    }
}
