package pl.pkozuch.poker.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.TestPreparer;

import java.util.stream.Stream;

public class ActionFactoryTests {


    private static Stream<?> provideValidActions() {
        //TODO: Add AllInAction
        return Stream.of(
//                Arguments.of(AllInAction.class, "ALLIN"),
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
                Arguments.of("RAISE 10 20"),
                Arguments.of("asdkas")
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidActions")
    public void testCreateValidAction(Class<Action> actionClass, String actionString) {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        ActionFactory actionFactory = new ActionFactory((GameController) objects[0]);
        Assertions.assertInstanceOf(actionClass, actionFactory.create((Player) objects[1], actionString));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidActions")
    public void testCreateInvalidAction(String actionString) {
        Object[] objects = TestPreparer.createGameControllerAndPlayers();

        ActionFactory actionFactory = new ActionFactory((GameController) objects[0]);
        Assertions.assertThrows(RuntimeException.class, () -> {
            actionFactory.create((Player) objects[1], actionString);
        });
    }

}
