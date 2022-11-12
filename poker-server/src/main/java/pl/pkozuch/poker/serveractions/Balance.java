package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class Balance extends ServerAction {

    Balance(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args != null)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");
    }

    public static String getHelpString() {
        return "BALANCE";
    }

    @Override
    public void validate() {
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        try {
            playerWrapper.sendMessageToPlayer("Twój stan konta to: " + playerWrapper.getPlayer().getBalance());
        } catch (Exception e) {
            playerWrapper.sendMessageToPlayer("Nie udało się sprawdzić stanu twojego konta. " + e.getMessage());
        }
    }
}
