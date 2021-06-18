package shared.serializable;

import shared.data.Movie;
import shared.util.CommandExecutionCode;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;

public class ServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private LinkedHashSet<Movie> movieSet;
    private final String responseBody;
    private final CommandExecutionCode code;
    private final List<String> responseBodyArgs;
//
//    public ServerResponse(CommandExecutionCode code, String responseToPrint, List<String> responseBodyArgs) {
//        this.code = code;
//        this.responseBody = responseToPrint;
//        this.responseBodyArgs = responseBodyArgs;
//    }

    public ServerResponse(CommandExecutionCode code, String responseToPrint, List<String> responseBodyArgs, LinkedHashSet<Movie> movieSet) {
        this.code = code;
        this.responseBody = responseToPrint;
        this.responseBodyArgs = responseBodyArgs;
        this.movieSet = movieSet;
    }

    public String getResponseToPrint() {
        return responseBody;
    }

    public CommandExecutionCode getCode() {
        return code;
    }

    public LinkedHashSet<Movie> getMovieSet() {
        return movieSet;
    }

    public List<String> getResponseBodyArgs() {
        return responseBodyArgs;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "responseToPrint='" + responseBody + '\'' +
                ", code=" + code +
                '}';
    }
}
