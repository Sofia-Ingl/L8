package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

import java.awt.*;

public class Help extends UserCommand {

    public Help() {
        super("help", "вывести справку по доступным командам", false, false, false);
    }


    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {


        CommandResultContainer container = new CommandResultContainer();
        container.setResult("HelpMessage");
        return new Pair<>(true, container);

    }
}
