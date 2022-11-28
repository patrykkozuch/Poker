package pl.pkozuch.poker.logic;

/**
 * Controlls to which players message should be sent
 */
public class MessageController {
    private final MessageBuilder messageBuilder;
    private final GameController gameController;

    /**
     * Creates message controller.
     *
     * @param gameController For which MessageController is being created
     */
    public MessageController(GameController gameController) {
        this.messageBuilder = new MessageBuilder(gameController);
        this.gameController = gameController;
    }

    /**
     * Shows balance message to all players.
     * For details: {@link MessageBuilder#buildHeaderMessage()}
     */
    public void showBalanceToAllPlayers() {
        gameController.sendMessageToAllPlayers(messageBuilder.buildHeaderMessage());
    }

    /**
     * Shows cards summary message to all players.
     * For details: {@link MessageBuilder#buildCardMessage}
     */
    public void showCardsSummaryToAllPlayers() {
        for (Player p : gameController.getGame().getAllPlayers()) {
            p.sendMessage(messageBuilder.buildCardMessage(p));
        }
    }

    /**
     * Shows bet message to player {@code p}
     * For details: {@link MessageBuilder#buildBetMessageForPlayer}
     *
     * @param p Players to whom message should be sent
     */
    public void showBetMessageToPlayer(Player p) {
        p.sendMessage(messageBuilder.buildBetMessageForPlayer(p));
    }

    /**
     * Shows change message to player {@code p}
     * For details: {@link MessageBuilder#buildChangeMessageForPlayer}
     *
     * @param p Players to whom message should be sent
     */
    public void showChangeMessageForPlayer(Player p) {
        p.sendMessage(MessageBuilder.buildChangeMessageForPlayer);
    }

    /**
     * Reads response to player. {@link Player#readFrom()}
     *
     * @param p Player from whom response should be read
     * @return Players response or null, if response has been invalid
     */
    public String getPlayerResponse(Player p) {
        return p.readFrom();
    }
}
