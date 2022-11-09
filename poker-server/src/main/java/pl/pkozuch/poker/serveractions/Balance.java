package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class Balance extends ServerAction {

    Balance(Server server, PlayerWrapper playerWrapper, String[] args) {
        super(server, playerWrapper);

        if (args != null)
            throw new RuntimeException("Nieprawidłowa liczba argumentów");
    }

    @Override
    public void validate() {
    }

    @Override
    public void make() {
        super.make();

        try {
            playerWrapper.sendMessageToPlayer("Twój stan konta to: " + playerWrapper.getPlayer().getBalance());
        } catch (Exception e) {
            playerWrapper.sendMessageToPlayer("Nie udało się sprawdzić stanu twojego konta. " + e.getMessage());
        }
    }

    public static String getHelpString() {
        return "BALANCE";
    }
}
