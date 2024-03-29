package pl.pkozuch.poker.serveractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pkozuch.poker.actions.Action;
import pl.pkozuch.poker.actions.NoSuchActionException;
import pl.pkozuch.poker.logic.NoSuchPlayerException;

import java.util.stream.Stream;

class ServerActionFactoryTests {
    private static Stream<?> provideValidActions() {
        return Stream.of(
                Arguments.of(BalanceAction.class, "BALANCE"),
                Arguments.of(BalanceAction.class, "balance"),
                Arguments.of(HelpAction.class, "HELP"),
                Arguments.of(CreateGameAction.class, "CREATE 0"),
                Arguments.of(ListGamesAction.class, "LIST"),
                Arguments.of(JoinGameAction.class, "JOIN 1"),
                Arguments.of(QuitGameAction.class, "QUIT"),
                Arguments.of(StartGameAction.class, "START")
        );
    }

    private static Stream<?> provideInvalidActions() {
        return Stream.of(
                Arguments.of("CREATE 4 4"),
                Arguments.of("JOIN -1")
        );
    }

    private static Stream<?> provideNonExistingActions() {
        return Stream.of(
                Arguments.of("efasd"),
                Arguments.of("6 change"),
                Arguments.of("1 1 1 1")
        );
    }

    private static Stream<?> provideInvalidPlayerIDActions() {
        return Stream.of(
                Arguments.of("-1 HELP"),
                Arguments.of("HELP"),
                Arguments.of("A HELP")
        );
    }

    @SuppressWarnings("JUnitMalformedDeclaration")
    @ParameterizedTest
    @MethodSource("provideValidActions")
    void testCreateValidAction(Class<Action> actionClass, String actionString) throws NoSuchActionException, NoSuchPlayerException {
        ServerStub s = new ServerStub();
        PlayerWrapperStub p = new PlayerWrapperStub();
        s.addPlayer(p);

        ServerActionFactory actionFactory = new ServerActionFactory(s);
        Assertions.assertInstanceOf(actionClass, actionFactory.create(p, String.format("%d %s", p.getPlayer().getId(), actionString)));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidActions")
    void testCreateInvalidAction(String actionString) {
        ServerStub s = new ServerStub();
        PlayerWrapperStub p = new PlayerWrapperStub();
        s.addPlayer(p);

        ServerActionFactory actionFactory = new ServerActionFactory(s);

        String finalActionString = String.format("%d %s", p.getPlayer().getId(), actionString);
        Assertions.assertThrows(IllegalArgumentException.class, () -> actionFactory.create(p, finalActionString));
    }

    @ParameterizedTest
    @MethodSource("provideNonExistingActions")
    void testCreateNonExistingActions(String actionString) {
        ServerStub s = new ServerStub();
        PlayerWrapperStub p = new PlayerWrapperStub();
        s.addPlayer(p);

        ServerActionFactory actionFactory = new ServerActionFactory(s);

        String finalActionString = String.format("%d %s", p.getPlayer().getId(), actionString);
        Assertions.assertThrows(NoSuchActionException.class, () -> actionFactory.create(p, finalActionString));
    }


    @ParameterizedTest
    @MethodSource("provideInvalidPlayerIDActions")
    void testCreateActionWithWrongPlayerID(String invalidAction) {
        ServerStub s = new ServerStub();
        PlayerWrapperStub p = new PlayerWrapperStub();
        s.addPlayer(p);

        ServerActionFactory actionFactory = new ServerActionFactory(s);

        Assertions.assertThrows(IllegalArgumentException.class, () -> actionFactory.create(p, invalidAction));
    }

    @Test
    void testCreateActionWithNonExistingPlayerID() {
        ServerStub s = new ServerStub();
        PlayerWrapperStub p = new PlayerWrapperStub();
        s.addPlayer(p);

        ServerActionFactory actionFactory = new ServerActionFactory(s);

        Assertions.assertThrows(NoSuchPlayerException.class, () -> actionFactory.create(p, "5 HELP"));
    }
}