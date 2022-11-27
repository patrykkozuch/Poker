package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

import java.util.stream.Stream;

public class ActionFactoryTests {

    private static Stream<?> provideValidActions() {
        return Stream.of(
                Arguments.of(AllInAction.class, "ALLIN"),
                Arguments.of(CallAction.class, "CALL"),
                Arguments.of(CallAction.class, "call"),
                Arguments.of(ChangeAction.class, "CHANGE 0"),
                Arguments.of(CheckAction.class, "CHECK"),
                Arguments.of(RaiseAction.class, "RAISE 10"),
                Arguments.of(FoldAction.class, "FOLD")
        );
    }

    private static Stream<?> provideInvalidActions() {
        return Stream.of(
                Arguments.of("CHANGE -1"),
                Arguments.of("CHANGE 6"),
                Arguments.of("CHANGE 1 2 3 4 4 4 4 4 4"),
                Arguments.of("RAISE -10"),
                Arguments.of("RAISE 10 20")
        );
    }

    private static Stream<?> provideNonExistingActions() {
        return Stream.of(
                Arguments.of("efasd"),
                Arguments.of("6 change"),
                Arguments.of("1 1 1 1")
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidActions")
    public void testCreateValidAction(Class<Action> actionClass, String actionString) throws NoSuchActionException, IllegalActionException {
        Game g = new Game(1, 1);

        GameController gameController = new GameController(g);

        Player p = new Player(new ChannelControllerStub(null, null));

        g.addPlayer(p);

        gameController.startGame();

        ActionFactory actionFactory = new ActionFactory(gameController);
        Assertions.assertInstanceOf(actionClass, actionFactory.create(p, String.format("%d %s", p.getId(), actionString)));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidActions")
    public void testCreateInvalidAction(String actionString) throws IllegalActionException {
        Game g = new Game(1, 1);

        GameController gameController = new GameController(g);

        Player p = new Player(new ChannelControllerStub(null, null));

        g.addPlayer(p);

        gameController.startGame();

        ActionFactory actionFactory = new ActionFactory(gameController);

        Assertions.assertThrows(IllegalArgumentException.class, () -> actionFactory.create(p, String.format("%d %s", p.getId(), actionString)));
    }

    @ParameterizedTest
    @MethodSource("provideNonExistingActions")
    public void testCreateNonExistingActions(String actionString) throws IllegalActionException {
        Game g = new Game(1, 1);

        GameController gameController = new GameController(g);

        Player p = new Player(new ChannelControllerStub(null, null));

        g.addPlayer(p);

        gameController.startGame();

        ActionFactory actionFactory = new ActionFactory(gameController);

        Assertions.assertThrows(NoSuchActionException.class, () -> actionFactory.create(p, String.format("%d %s", p.getId(), actionString)));
    }

    @Test
    public void testCreateActionWithWrongPlayerID() throws IllegalActionException {
        Game g = new Game(1, 1);

        GameController gameController = new GameController(g);

        Player p = new Player(new ChannelControllerStub(null, null));

        g.addPlayer(p);

        gameController.startGame();

        ActionFactory actionFactory = new ActionFactory(gameController);

        Assertions.assertThrows(IllegalArgumentException.class, () -> actionFactory.create(p, String.format("%d %s", -1, "FOLD")));
    }

    @Test
    public void testCreateActionWithoutPlayerID() throws IllegalActionException {
        Game g = new Game(1, 1);

        GameController gameController = new GameController(g);

        Player p = new Player(new ChannelControllerStub(null, null));

        g.addPlayer(p);

        gameController.startGame();

        ActionFactory actionFactory = new ActionFactory(gameController);

        Assertions.assertThrows(IllegalArgumentException.class, () -> actionFactory.create(p, "FOLD"));
    }
}
