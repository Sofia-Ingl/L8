package server.commands.inner;

import server.commands.abstracts.InnerServerCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

public class Register extends InnerServerCommand {

    public Register() {
        super("register", "зарегистрировать пользователя");
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {
            boolean successfullyInserted = getUserHandler().insertUser(user);
            if (!successfullyInserted) {
                throw new AccessDeniedException("UserInsertionError");
            }
            return new Pair<>(true, container);
        } catch (SQLException e) {
            container.setResult("RegisterError");
        } catch (AccessDeniedException e) {
            container.setResult(e.getMessage());
        }
        return new Pair<>(false, container);
    }
}
