package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import server.util.CollectionStorage;
import shared.serializable.Pair;
import shared.serializable.User;

public class Info extends UserCommand {

    public Info() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", false, false, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        container.setResult("Info");

        CollectionStorage storage = getCollectionStorage();
        container.addResultArg(storage.getTypes()[0].toString());
        container.addResultArg(storage.getTypes()[1].toString());
        container.addResultArg(String.valueOf(storage.getCollection().size()));
        container.addResultArg(storage.getInitTime().toString());
        /*
        info = "\n" + "ИНФОРМАЦИЯ О КОЛЛЕКЦИИ" + "\n"
                + "Тип коллекции: " + storage.getTypes()[0] + ", тип хранимых объектов: " + storage.getTypes()[1] + "\n"
                + "Количество объектов: " + storage.getCollection().size() + "\n"
                + "Время инициализации: " + storage.getInitTime() + "\n"
                + "Время последнего обновления: " + storage.getUpdateTime() + "\n"
                + "Время последнего доступа: " + storage.getLastAccessTime() + "\n"
                + "Максимальный элемент: " + storage.getMaxMovie() + "\n";


         */
        return new Pair<>(true, container);

    }
}
