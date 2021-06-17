package client.util;

import client.Client;
import shared.data.Movie;
import shared.exceptions.ScriptException;
import shared.serializable.ClientRequest;
import shared.serializable.Pair;

import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class ScriptProcessor {

    private Stack<String> scriptStack;
    private Stack<Scanner> scannerStack;
    private boolean done = false;
    private UserElementGetter userElementGetter;
    private Scanner currentScanner;
    private Map<String, Pair<Boolean, Boolean>> availableCommands;
    private Client client;

    public ScriptProcessor(UserElementGetter userElementGetter) {
        this.scannerStack = new Stack<>();
        this.scriptStack = new Stack<>();
        this.userElementGetter = userElementGetter;
        this.currentScanner = null;
        userElementGetter.setScanner(null);
        initMap();
    }

    private void initMap() {
        availableCommands = new HashMap<>();
        availableCommands.put("add", new Pair<>(true, false));
        availableCommands.put("add_if_max", new Pair<>(true, false));
        availableCommands.put("clear", new Pair<>(false, false));
        availableCommands.put("execute_script", new Pair<>(false, true));
        availableCommands.put("filter_greater_than_golden_palm_count", new Pair<>(false, true));
        availableCommands.put("help", new Pair<>(false, false));
        availableCommands.put("history", new Pair<>(false, false));
        availableCommands.put("info", new Pair<>(false, false));
        availableCommands.put("remove_all_by_screenwriter", new Pair<>(false, true));
        availableCommands.put("remove_by_id", new Pair<>(false, true));
        availableCommands.put("remove_greater", new Pair<>(true, false));
        availableCommands.put("update", new Pair<>(true, true));
    }

    public void setFirstScript(String path) throws ScriptException {
        removeAllFromStack();
        putScriptOnStack(path);
        done = false;
    }

    private void putScriptOnStack(String scriptName) throws ScriptException {
        Path newScriptFile = Paths.get(scriptName);
        try {
            String realName = newScriptFile.toRealPath().toAbsolutePath().toString();
            if (validatePath(realName)) {
                scriptStack.push(realName);
                Scanner scanner = new Scanner(newScriptFile);
                scannerStack.push(scanner);
                userElementGetter.setScanner(scanner);
                currentScanner = scanner;
            } else {
                removeAllFromStack();
                throw new ScriptException();
            }
        } catch (IOException e) {
            removeAllFromStack();
            throw new ScriptException();
        }
    }

    private boolean validatePath(String realPath) {
        boolean condition1 = realPath.trim().startsWith("/dev");
        boolean condition2 = scriptStack.contains(realPath);
        return !condition1 && !condition2;
    }

    private void removeLastFromStack() {
        Scanner scannerToClose = scannerStack.pop();
        scannerToClose.close();
        scriptStack.pop();
        if (!scriptStack.isEmpty()) {
            userElementGetter.setScanner(scannerStack.peek());
        } else {
            userElementGetter.setScanner(null);
        }
    }

    public void removeAllFromStack() {
        Scanner scannerToClose;
        while (!scannerStack.isEmpty()) {
            scannerToClose = scannerStack.pop();
            scannerToClose.close();
        }
        scriptStack.clear();
        userElementGetter.setScanner(null);
    }


    public ClientRequest generateRequest() {

        while (!currentScanner.hasNextLine()) {
            removeLastFromStack();
            if (scriptStack.isEmpty()) {
                done = true;
                return null;
            }
        }

        String[] commandWithArg = (currentScanner.nextLine() + " ").split(" ", 2);
        String command = commandWithArg[0].trim();
        if (command.isEmpty()) {
            return null;
        }
        String commandArg = commandWithArg[1].trim();
        boolean validation = commandIsValid(command, commandArg);

        if (!validation) {
            removeAllFromStack();
            throw new ScriptException();
        }

        if (command.equals("execute_script")){
            putScriptOnStack(commandArg);
            return null;
        }

        Movie movie = null;
        if (availableCommands.get(command).getFirst()) {
            try {
                movie = userElementGetter.movieGetter();
                movie.setOwner(client.getUser());
            } catch (EOFException e) {
                removeAllFromStack();
                throw new ScriptException();
            }
        }

        return new ClientRequest(command, commandArg, movie, client.getUser());
    }

    private boolean commandIsValid(String command, String commandArg) {
        Pair<Boolean, Boolean> commandDescription = availableCommands.get(command);
        Boolean hasStringArg;
        if (commandDescription == null) return false;
        hasStringArg = commandDescription.getSecond();
        return !(hasStringArg.equals(commandArg.isEmpty()));

        /*
        if (!hasStringArg && !commandArg.isEmpty()) return false;
        if (hasStringArg && commandArg.isEmpty()) return false;
        return true;
         */

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isDone() {
        return done;
    }
}
