package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.util.CollectionStorage;
import shared.serializable.Pair;
import shared.serializable.User;

public class Info extends UserCommand {

    public Info() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", false, false, true);
    }

    @Override
    public Pair<Boolean, String> execute(String arg, Object obj, User user) {

        String info;

        CollectionStorage storage = getCollectionStorage();
        info = "\n" + "ИНФОРМАЦИЯ О КОЛЛЕКЦИИ" + "\n"
                + "Тип коллекции: " + storage.getTypes()[0] + ", тип хранимых объектов: " + storage.getTypes()[1] + "\n"
                + "Количество объектов: " + storage.getCollection().size() + "\n"
                + "Время инициализации: " + storage.getInitTime() + "\n"
                + "Время последнего обновления: " + storage.getUpdateTime() + "\n"
                + "Время последнего доступа: " + storage.getLastAccessTime() + "\n"
                + "Максимальный элемент: " + storage.getMaxMovie() + "\n";

        return new Pair<>(true, info);

    }
}
