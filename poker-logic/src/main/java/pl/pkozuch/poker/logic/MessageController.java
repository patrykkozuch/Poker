package pl.pkozuch.poker.logic;

import java.util.ArrayList;

public class MessageController {
    private final MessageBuilder messageBuilder;
    private final GameController gameController;
    private final ArrayList<Player> players;

    public MessageController(GameController gameController, ArrayList<Player> players) {
        this.messageBuilder = new MessageBuilder(gameController);
        this.gameController = gameController;
        this.players = players;
    }

    public void showBalanceToPlayer(Player p) {
        p.sendMessage(messageBuilder.buildHeaderMessage());
    }

    public void showBalanceToAllPlayers() {
        gameController.sendMessageToAllPlayers(messageBuilder.buildHeaderMessage());
    }

    public void showCardsSummaryToAllPlayers() {
        for (Player p : players) {
            p.sendMessage(messageBuilder.buildCardMessage(p));
        }
    }

    public void showCardsSummaryToPlayer(Player p) {
        p.sendMessage(messageBuilder.buildCardMessage(p));
    }

    public void showBetMessageToPlayer(Player p) {
        p.sendMessage(messageBuilder.buildBetMessageForPlayer(p));
    }

    public void showChangeMessageForPlayer(Player p) {
        p.sendMessage(messageBuilder.buildChangeMessageForPlayer(p));
    }

    public String getPlayerResponse(Player p) {
        return p.getResponse();
    }
}
