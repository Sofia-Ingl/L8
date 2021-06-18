package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

import java.awt.*;

public class Help extends UserCommand {

    private String commandInfo = null;

    public Help() {
        super("help", "вывести справку по доступным командам", false, false, false);
    }


    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {


        CommandResultContainer container = new CommandResultContainer();
        container.setResult("HelpMessage");
//        if (commandInfo == null) {
//
//            StringBuilder help = new StringBuilder("\n" + "ИНФОРМАЦИЯ О ДОСТУПНЫХ КОМАНДАХ" + "\n");
//
//            for (UserCommand userCommand : getCommandWrapper().getAllCommandsAvailable().values()) {
//                help.append(userCommand.getName()).append(" ~> ").append(userCommand.getUtility()).append("\n");
//            }
//
//            commandInfo = help.toString();
//        }
        return new Pair<>(true, container);

    }
}
