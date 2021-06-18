package server.commands.user;

import server.commands.abstracts.Command;
import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

import java.util.ArrayList;

public class History extends UserCommand {

    public History() {
        super("history", "вывести последние 6 команд (без их аргументов)", false, false, false);
    }


    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        container.setResult("History");
        ArrayList<Command> history = getCommandWrapper().getHistory();
        for (int index = 0; index < history.size(); index++) {
            container.addResultArg(history.get(history.size() - 1 - index).getName());
        }

        return new Pair<>(true, container);
    }
}
