package pl.pkozuch.poker.serveractions;

import org.reflections.Reflections;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;

public class Help extends ServerAction {

    Help(Server server, PlayerWrapper playerWrapper, String[] args) {
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

        Reflections reflection = new Reflections("pl.pkozuch.poker.serveractions");

        List<Class<? extends ServerAction>> subTypes = reflection.getSubTypesOf(ServerAction.class).stream().sorted(Comparator.comparing(Class::getSimpleName)).toList();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Dostępne polecenia:\n");
        try {
            for (Class<? extends ServerAction> c :
                    subTypes) {
                stringBuilder.append(String.format("\t* %1$-20s\n", c.getMethod("getHelpString").invoke(c)));
            }
            playerWrapper.sendMessageToPlayer(stringBuilder.toString());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String getHelpString() {
        return "HELP";
    }
}
