package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

/**
 * Balance action - sends Player back his actual balance
 */
public class BalanceAction extends ServerAction {

    @SuppressWarnings("unused")
    @UsedViaReflection
    public static final String HELP_STRING = "BALANCE";

    /**
     * @param server        {@link ServerAction#server}
     * @param playerWrapper {@link ServerAction#playerWrapper}
     * @throws IllegalArgumentException {@code args} != null
     */
    BalanceAction(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args != null)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");
    }

    @Override
    protected void validate() {
        // Validation not needed
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        playerWrapper.sendMessageToPlayer("Twój stan konta to: " + playerWrapper.getPlayer().getBalance());
    }
}
