package shared.serializable;

import java.io.Serializable;

public class ClientRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String command;
    private final String commandArgument;
    private final Serializable objectArg;
    private final User user;

    public ClientRequest(String command, String commandArgument, Serializable objectArg, User user) {
        this.command = command;
        this.commandArgument = commandArgument;
        this.objectArg = objectArg;
        this.user = user;
    }

    public Object getCreatedObject() {
        return objectArg;
    }

    public String getCommand() {
        return command;
    }

    public String getCommandArgument() {
        return commandArgument;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "command='" + command + '\'' +
                ", commandArgument='" + commandArgument + '\'' +
                ", createdObject=" + objectArg.toString() +
                '}';
    }
}
