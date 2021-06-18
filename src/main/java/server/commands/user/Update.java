package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.data.Movie;
import shared.serializable.Pair;
import shared.serializable.User;

import java.sql.SQLException;

/**
 * Команда, обновляющая значение элемента с заданным айди.
 */
public class Update extends UserCommand {

    public Update() {
        super("update", "обновить значение элемента коллекции, id которого равен заданному", true, true, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {
            if (!arg.trim().matches("\\d+")) {
                throw new IllegalArgumentException("Неправильный тип аргумента к команде!");
            } else {
                int id = Integer.parseInt(arg.trim());
                Movie m2 = (Movie) obj;

                getDatabaseCollectionHandler().updateMovieById(id, m2, user);

                Movie m1 = getCollectionStorage().getByUserAndId(id, user);
                if (m1 != null) {
                    m1.setName(m2.getName());
                    m1.setCoordinates(m2.getCoordinates());
                    m1.setGenre(m2.getGenre());
                    m1.setOscarsCount(m2.getOscarsCount());
                    m1.setGoldenPalmCount(m2.getGoldenPalmCount());
                    m1.setTagline(m2.getTagline());
                    m1.setScreenwriter(m2.getScreenwriter());
                } else throw new IllegalAccessException();
            }

            return new Pair<>(true, container);

        } catch (IllegalArgumentException | SQLException e) {
            container.setResult("UpdateError");
        } catch (IllegalAccessException e) {
            container.setResult("AccessError");
        }
        return new Pair<>(false, container);
    }
}