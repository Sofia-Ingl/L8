package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

import java.sql.SQLException;

/**
 * Команда, удаляющая элементы по айди.
 */
public class RemoveById extends UserCommand {

    public RemoveById() {
        super("remove_by_id", "удалить элемент из коллекции по его id", false, true, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {
            if (!arg.trim().matches("\\d+")) {
                throw new IllegalArgumentException();
            }

            int id = Integer.parseInt(arg.trim());
            getDatabaseCollectionHandler().deleteMovieById(id, user);
            getCollectionStorage().deleteElementForId(id, user);

            return new Pair<>(true, container);

        } catch (IllegalArgumentException | SQLException e) {
            container.setResult("RemoveError");
        }
        return new Pair<>(false, container);
    }
}
