package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

import java.awt.*;

public class Exit extends UserCommand {

    public Exit() {
        super("exit", "завершить программу", false, false, false);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        return new Pair<>(true, container);

    }
}

