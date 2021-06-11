package client.util;

import shared.data.Movie;
import shared.serializable.ClientRequest;
import shared.serializable.Pair;
import shared.serializable.User;
import shared.util.CommandExecutionCode;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Interaction extends InteractiveConsoleUtils {

    private User user = null;

    private final Scanner defaultScanner;
    private final UserElementGetter userElementGetter;
    /**
     * HashMap<String, Pair<String, Pair<Boolean, Boolean>>> commandsAvailable
     * Ключ - название команды
     * Значение - пара, где 1 элемент - описание команды, второй элмент - пара булевых значений;
     * Первое из них говорит, интерактивна ли команда. Второе - принимает ли она строчной аргумент.
     */
    private HashMap<String, Pair<String, Pair<Boolean, Boolean>>> commandsAvailable = null;
    private boolean isScript = false;
    private final Stack<Path> files = new Stack<>();
    private final Stack<Scanner> scanners = new Stack<>();


    public Interaction(InputStream in, OutputStream out, UserElementGetter userElementGetter) {
        setIn(in);
        setOut(out);
        defaultScanner = new Scanner(in);
        this.userElementGetter = userElementGetter;

        this.userElementGetter.setIn(in);
        this.userElementGetter.setOut(out);
        this.userElementGetter.setScanner(defaultScanner);
    }


    public ClientRequest formRequest(CommandExecutionCode code) {

        String[] commandWithArg;
        Movie movie = null;
        String command = "";
        String commandArg = "";
        boolean validation = false;

        if (!isScript) {

            while (!validation) {

                printMessage(">");
                systemInClosedProcessing();
                commandWithArg = (defaultScanner.nextLine() + " ").split(" ", 2);
                command = commandWithArg[0].trim();
                commandArg = commandWithArg[1].trim();
                validation = commandIsValid(command, commandArg);
                if (!validation) {
                    printlnMessage("Команды с таким именем нет или вы используете команду неверно! Вызовите help для справки");
                }
            }

            try {
                if (commandsAvailable.get(command).getSecond().getFirst()) {
                    movie = userElementGetter.movieGetter();
                    movie.setOwner(user);
                }
            } catch (EOFException e) {
                System.exit(1);
            }

            if (command.equals("execute_script")) {
                boolean success = putScriptOnStack(commandArg);
                if (!success) {
                    return null;
                }
            }

        } else {

            if (code == CommandExecutionCode.ERROR) {
                removeAllFromStack();
                return null;
            }
            while (!getScanner().hasNextLine()) {
                removeLastFromStack();
                if (!isScript) {
                    return null;
                }
            }

            commandWithArg = (getScanner().nextLine() + " ").split(" ", 2);
            command = commandWithArg[0].trim();
            if (command.isEmpty()) {
                return null;
            }
            commandArg = commandWithArg[1].trim();
            validation = commandIsValid(command, commandArg);
            if (!validation) {
                printlnMessage("В скрипте обнаружена ошибка!");
                removeAllFromStack();
                return null;
            }

            printMessage(">");
            printlnMessage(command + " " + commandArg);

            try {
                if (commandsAvailable.get(command).getSecond().getFirst()) {
                    movie = userElementGetter.movieGetter();
                    movie.setOwner(user);
                }
            } catch (EOFException e) {
                printlnMessage("Ошибка в скрипте");
                removeAllFromStack();
                return null;
            }

            if (command.equals("execute_script")) {
                boolean success = putScriptOnStack(commandArg);
                if (!success) {
                    removeAllFromStack();
                    return null;
                }
            }

            if (command.equals("exit")) {
                removeAllFromStack();
            }

        }

        return new ClientRequest(command, commandArg, movie, user);
    }

    private boolean putScriptOnStack(String path) {

        try {

            Path realPath = Paths.get(path).toRealPath();
            if (realPath.toString().length() > 3 && realPath.toString().trim().startsWith("/dev")) {
                printlnMessage("Пошалить вздумал?) Не в мою смену, братишка!");
                return false;
            }

            if (files.contains(realPath)) {
                printlnMessage("Рекурсия в скрипте!!!");
                return false;
            }

            isScript = true;
            setScanner(new Scanner(realPath));

            files.push(realPath);
            scanners.push(getScanner());

            userElementGetter.setScanner(getScanner());
            userElementGetter.setSuppressMessages(true);
            userElementGetter.setScript(true);

            return true;

        } catch (IOException | SecurityException | IllegalArgumentException e) {
            printlnMessage("Ой все! Кажется, кто-то подсунул паленый скрипт :c Признавайся, это ты сделал?!");
        }
        return false;
    }

    private void removeLastFromStack() {
        Scanner scannerToClose = scanners.pop();
        scannerToClose.close();
        printlnMessage("Выход из скрипта " + files.pop().toString());
        if (scanners.isEmpty()) {
            isScript = false;
            setScanner(null);
            userElementGetter.setScanner(defaultScanner);
            userElementGetter.setSuppressMessages(false);
            userElementGetter.setScript(false);
        } else {
            setScanner(scanners.peek());
            userElementGetter.setScanner(getScanner());
        }
    }

    public void removeAllFromStack() {
        Scanner scannerToClose;
        while (!scanners.isEmpty()) {
            scannerToClose = scanners.pop();
            scannerToClose.close();
        }
        isScript = false;
        setScanner(null);
        userElementGetter.setScanner(defaultScanner);
        userElementGetter.setSuppressMessages(false);
        userElementGetter.setScript(false);
        files.clear();
    }


    private boolean commandIsValid(String command, String commandArg) {
        Pair<String, Pair<Boolean, Boolean>> commandInfo = commandsAvailable.get(command);
        if (commandInfo == null) return false;
        boolean stringArgValidation = false;
        if ((commandInfo.getSecond().getSecond() && !commandArg.equals("")) || (!commandInfo.getSecond().getSecond() && commandArg.equals(""))) {
            stringArgValidation = true;
        }
        return stringArgValidation;
    }


    public void setCommandsAvailable(HashMap<String, Pair<String, Pair<Boolean, Boolean>>> commandsAvailable) {
        this.commandsAvailable = commandsAvailable;
    }

    public HashMap<String, Pair<String, Pair<Boolean, Boolean>>> getCommandsAvailable() {
        return commandsAvailable;
    }

    public String readLine() {
        systemInClosedProcessing();
        return defaultScanner.nextLine();
    }

    public String showCommandsAvailable() {

        StringBuilder builder = new StringBuilder();
        if (commandsAvailable != null && !commandsAvailable.isEmpty()) {
            builder.append("\n").append("СПИСОК ДОСТУПНЫХ КОМАНД").append("\n");
            for (String command : commandsAvailable.keySet()) {
                builder.append(command).append(": ");
                builder.append((commandsAvailable.get(command).getSecond().getFirst()) ? "" : "не ").append("интерактивна").append("; ");
                builder.append((commandsAvailable.get(command).getSecond().getSecond()) ? "" : "не ").append("принимает строчной аргумент");
                builder.append("\n");
            }
            return builder.toString();
        }
        return "Нет доступных команд";
    }


    private void systemInClosedProcessing() {
        if (defaultScanner == null || !defaultScanner.hasNextLine()) {
            printlnMessage("\nПоток ввода завершен, приложение будет закрыто");
            System.exit(0);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
