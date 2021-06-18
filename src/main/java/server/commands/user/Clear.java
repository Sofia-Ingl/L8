package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

import java.sql.SQLException;

public class Clear extends UserCommand {

    public Clear() {
        super("clear", "очистить коллекцию", false, false, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {

            getDatabaseCollectionHandler().deleteAllMoviesBelongToUser(user);
            getCollectionStorage().deleteElementsByUser(user);

            return new Pair<>(true, container);
        } catch (SQLException e) {
            container.setResult("ClearError");
        }
        return new Pair<>(false, container);
    }
}
