package server.commands.user;


import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.data.Movie;
import shared.serializable.Pair;
import shared.serializable.User;

import java.sql.SQLException;


public class RemoveGreater extends UserCommand {

    public RemoveGreater() {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный", true, false, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        Movie movieToCompareWith = (Movie) obj;

        try {
            getDatabaseCollectionHandler().removeGreaterMovies(movieToCompareWith, user, getCollectionStorage());
            getCollectionStorage().removeGreater(movieToCompareWith, user);

            return new Pair<>(true, container);
        } catch (SQLException e) {
            container.setResult("RemoveGreaterError");
        }
        return new Pair<>(false, container);
    }
}

