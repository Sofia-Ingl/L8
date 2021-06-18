package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.data.Movie;
import shared.serializable.Pair;
import shared.serializable.User;

import java.sql.SQLException;

public class AddIfMax extends UserCommand {

    public AddIfMax() {
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", true, false, true);
    }


    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        Movie newMovie = (Movie) obj;
        try {

            if (getCollectionStorage().getMaxMovie() == null || getCollectionStorage().getMaxMovie().compareTo(newMovie) < 0) {

                newMovie = getDatabaseCollectionHandler().addNewMovie(newMovie, user);
                getCollectionStorage().addMovie(newMovie);
            }

            return new Pair<>(true, container);

        } catch (SQLException e) {
            container.setResult("AddIfMaxError");
        }
        return new Pair<>(false, container);
    }
}
