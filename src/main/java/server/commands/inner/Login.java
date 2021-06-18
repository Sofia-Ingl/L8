package server.commands.inner;

import server.commands.abstracts.InnerServerCommand;
import server.commands.util.CommandResultContainer;
import server.util.DatabaseCollectionHandler;
import shared.serializable.Pair;
import shared.serializable.User;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.security.AccessControlException;
import java.sql.SQLException;

public class Login extends InnerServerCommand {

    public Login() {
        super("login", "обеспечить зарегистрированному пользователю вход в систему");
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {
            if (!getUserHandler().findUserByNameAndPass(user)) {
                throw new AccessDeniedException("WrongNameOrPassError");
            }
            return new Pair<>(true, container);
        } catch (SQLException e) {
            container.setResult("LoginError");
        } catch (AccessDeniedException e) {
            container.setResult(e.getMessage());
        }
        return new Pair<>(false, container);
    }
}
