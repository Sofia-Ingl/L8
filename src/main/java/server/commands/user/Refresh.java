package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.data.Movie;
import shared.serializable.Pair;
import shared.serializable.User;

public class Refresh extends UserCommand {

    public Refresh() {
        super("refresh", "", false, false, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
//
//        if (getCollectionStorage().getCollection().isEmpty()) {
//            responseString = new StringBuilder("Коллекция пуста!");
//        } else {
//            responseString = new StringBuilder("В настоящий момент в коллекции находятся следующие элементы\n");
//            for (Movie movie : getCollectionStorage().getSortedCollection().getSecond()) {
//                responseString.append(movie).append("\n");
//            }
//        }
        return new Pair<>(true, container);

    }

}
