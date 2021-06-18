package server.commands.abstracts;

import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

public interface Executables {
    Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user);
}
