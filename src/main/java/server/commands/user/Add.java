package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import server.util.DatabaseCollectionHandler;
import shared.data.Movie;
import shared.serializable.Pair;
import shared.serializable.User;

import java.sql.SQLException;

public class Add extends UserCommand {

    public Add() {
        super("add", "добавить новый элемент в коллекцию", true, false, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {

            Movie movie = getDatabaseCollectionHandler().addNewMovie((Movie) obj, user);
            getCollectionStorage().addMovie(movie);

            return new Pair<>(true, container);

        } catch (SQLException e) {
            container.setResult("AddError");
        }

        return new Pair<>(false, container);
    }
}
