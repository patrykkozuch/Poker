package pl.pkozuch.poker.logic;

import java.util.List;

public class MessageController {
    private final MessageBuilder messageBuilder;
    private final GameController gameController;
    private final List<Player> players;

    public MessageController(GameController gameController, List<Player> players) {
        this.messageBuilder = new MessageBuilder(gameController);
        this.gameController = gameController;
        this.players = players;
    }

    public void showBalanceToAllPlayers() {
        gameController.sendMessageToAllPlayers(messageBuilder.buildHeaderMessage());
    }

    public void showCardsSummaryToAllPlayers() {
        for (Player p : players) {
            p.sendMessage(messageBuilder.buildCardMessage(p));
        }
    }

    public void showBetMessageToPlayer(Player p) {
        p.sendMessage(messageBuilder.buildBetMessageForPlayer(p));
    }

    public void showChangeMessageForPlayer(Player p) {
        p.sendMessage(MessageBuilder.buildChangeMessageForPlayer);
    }

    public String getPlayerResponse(Player p) {
        return p.readFrom();
    }
}
