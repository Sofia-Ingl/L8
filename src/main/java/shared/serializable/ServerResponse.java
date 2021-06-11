package shared.serializable;

import shared.data.Movie;
import shared.util.CommandExecutionCode;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class ServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private LinkedHashSet<Movie> movieSet;
    private final String responseBody;
    private final CommandExecutionCode code;

    public ServerResponse(CommandExecutionCode code, String responseToPrint) {
        this.code = code;
        this.responseBody = responseToPrint;
    }

    public String getResponseToPrint() {
        return responseBody;
    }

    public CommandExecutionCode getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "responseToPrint='" + responseBody + '\'' +
                ", code=" + code +
                '}';
    }
}
