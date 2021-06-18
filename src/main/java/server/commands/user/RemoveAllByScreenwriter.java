package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

import java.sql.SQLException;

public class RemoveAllByScreenwriter extends UserCommand {

    public RemoveAllByScreenwriter() {
        super("remove_all_by_screenwriter", "удалить из коллекции все элементы, значение поля screenwriter которого эквивалентно заданному", false, true, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {
            getDatabaseCollectionHandler().removeMoviesByScreenwriter(arg.trim(), user);
            getCollectionStorage().removeByScreenwriter(arg.trim(), user);

            return new Pair<>(true, container);
        } catch (SQLException e) {
            container.setResult("RemoveByScreenwriterError");
        }
        return new Pair<>(false, container);
    }
}
