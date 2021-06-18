package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

/**
 * Команда, исполняющая скрипт.
 */
public class ExecuteScript extends UserCommand {

    public ExecuteScript() {
        super("execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", false, true, false);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("ScriptError");
            }
            return new Pair<>(true, container);

        } catch (IllegalArgumentException e) {
            container.setResult(e.getMessage());
        }
        return new Pair<>(false, container);
    }
}
