package server.commands.util;

import java.util.ArrayList;
import java.util.List;

public class CommandResultContainer {

    private String result = "";
    private final List<String> resultArgs = new ArrayList<>();

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void addResultArg(String arg) {
        resultArgs.add(arg);
    }

    public List<String> getResultArgs() {
        return resultArgs;
    }
}
