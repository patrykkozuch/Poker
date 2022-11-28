package pl.pkozuch.poker.serveractions;

import org.reflections.Reflections;
import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

import java.util.Comparator;
import java.util.List;

/**
 * Help Action shows list of available actions
 */
public class HelpAction extends ServerAction {

    @SuppressWarnings("unused")
    @UsedViaReflection
    public static final String HELP_STRING = "HELP";

    /**
     * @param server        {@link ServerAction#server}
     * @param playerWrapper {@link ServerAction#playerWrapper}
     * @throws IllegalArgumentException if {@code args} != null
     */
    HelpAction(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
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

        Reflections reflection = new Reflections("pl.pkozuch.poker.serveractions");

        List<Class<? extends ServerAction>> subTypes = reflection.getSubTypesOf(ServerAction.class).stream().sorted(Comparator.comparing(Class::getSimpleName)).toList();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Dostępne polecenia:\n");

        try {
            for (Class<? extends ServerAction> c :
                    subTypes) {
                stringBuilder.append(String.format("\t* %1$-20s%n", c.getField("HELP_STRING").get(null)));
            }
            playerWrapper.sendMessageToPlayer(stringBuilder.toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
