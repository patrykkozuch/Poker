package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

public class ChangeAction extends Action {

    private final Integer[] cardIndexesToChange;

    ChangeAction(GameController gameController, Player player, String[] args) throws IllegalArgumentException {
        super(gameController, player);

        if (args.length < 1 || args.length > 5)
            throw new IllegalArgumentException("Podano nieprawidłową ilość kart. Można wybrać od 0 do 4 kart. Jeżeli nie chcesz wymienić żadnej karty, wpisz 'CHANGE 0'");

        if (!IntValidator.isInt(args))
            throw new IllegalArgumentException("Podano nieprawidłowy argument. Numer karty musi być liczbą");

        cardIndexesToChange = new Integer[args.length];

        for (int i = 0; i < args.length; i++) {
            cardIndexesToChange[i] = Integer.parseInt(args[i]) - 1;

            if ((cardIndexesToChange[i] < 0 && i != 0) || cardIndexesToChange[i] >= 5)
                throw new IllegalArgumentException("Podano nieprawidłową liczbę. Numer karty musi być liczbą z zakresu 1 do 5.");
        }
    }

    @Override
    public void validate() throws IllegalActionException {
        if (gameController.getRoundState() != GameController.possibleRoundStates.CHANGING)
            throw new IllegalActionException("Nie udało się wykonać akcji. Wymiana kart możliwa jest tylko w fazie wymiany.");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        if (cardIndexesToChange[0] != -1) {
            for (Integer idx : cardIndexesToChange) {
                player.changeCard(idx, gameController.getDeck().draw());
            }
        }

        player.changeCards();
    }

    @Override
    public void sendMessageToOtherPlayers() {
        gameController.sendMessageToAllPlayersWithout(
                player,
                String.format("Gracz Player %1d wymienił %2d kart", player.getId(), (cardIndexesToChange[0] == -1 ? 0 : cardIndexesToChange.length))
        );
    }
}
