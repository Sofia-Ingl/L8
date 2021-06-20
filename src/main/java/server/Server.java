package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.commands.abstracts.InnerServerCommand;
import server.commands.abstracts.UserCommand;
import server.commands.inner.Login;
import server.commands.inner.Register;
import server.commands.user.*;
import server.util.*;
import shared.serializable.Pair;

import java.io.IOException;
import java.net.*;
import java.nio.channels.IllegalBlockingModeException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    public final static Logger logger = LoggerFactory.getLogger(Server.class);

    private final int port;
    private ServerSocket serverSocket;
    private final CommandWrapper commandWrapper;
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);


    public static void main(String[] args) {

        Pair<String[], Integer> databaseAddrUserAndPort = processArguments(args);
        DatabaseManager databaseManager = new DatabaseManager(databaseAddrUserAndPort.getFirst()[0], databaseAddrUserAndPort.getFirst()[1], databaseAddrUserAndPort.getFirst()[2]);
        UserHandler userHandler = new UserHandler(databaseManager);
        DatabaseCollectionHandler databaseCollectionHandler = new DatabaseCollectionHandler(databaseManager, userHandler);
        CollectionStorage collectionStorage = new CollectionStorage(databaseCollectionHandler);
        collectionStorage.loadCollectionFromDatabase();

        InnerServerCommand[] innerServerCommands = {new Login(), new Register()};
        UserCommand refreshCommand = new Refresh();
        refreshCommand.setWrittenToHistory(false);
        UserCommand[] userCommands = {refreshCommand, new Help(), new History(), new Clear(), new Add(), new ExecuteScript(),
                new GoldenPalmsFilter(), new Info(), new AddIfMax(), new RemoveAllByScreenwriter(),
                new RemoveById(), new RemoveGreater(), new Update(), new Exit()};


        Server server = new Server(databaseAddrUserAndPort.getSecond(), new CommandWrapper(collectionStorage, databaseCollectionHandler, userHandler, userCommands, innerServerCommands));
        addShutdownHook(databaseManager, server.fixedThreadPool);

        server.run();
        databaseManager.closeConnectionWithDatabase();
    }


    Server(int port, CommandWrapper commandWrapper) {
        this.port = port;
        this.commandWrapper = commandWrapper;
    }

    @Override
    public void run() {

        logger.info("Сервер запускается...");
        createSocketFactory();
        boolean noServerExitCode = true;

        Thread exitManager = new Thread(new ExitManager());
        exitManager.start();

        while (noServerExitCode) {

            try {

                Socket socket = establishClientConnection();

                fixedThreadPool.submit(new ClientConnection(this, socket));

            } catch (ConnectException e) {
                logger.info(e.getMessage());
                noServerExitCode = false;
            } catch (IllegalThreadStateException e) {
                logger.info("Ошибка при запуске потока для обслуживания клиентского соединения");
            }
        }

        if (serverSocket != null) {
            try {
                serverSocket.close();
                logger.info("Сервер прекращает работу");
            } catch (IOException e) {
                logger.warn("Сервер прекращает работу с ошибкой");
            }
        }

        fixedThreadPool.shutdown();


    }

    private void createSocketFactory() {

        try {
            serverSocket = new ServerSocket(port);
            logger.info("Фабрика сокетов создана");
        } catch (BindException e) {
            logger.warn("Порт недоступен, следует указать другой");
            emergencyExit();
        } catch (IOException | IllegalArgumentException e) {
            logger.warn("Ошибка при инициализации фабрики сокетов");
            emergencyExit();
        }
    }

    private Socket establishClientConnection() throws ConnectException {
        try {
            logger.info("Прослушивается порт {}", port);
            Socket clientSocket = serverSocket.accept();
            logger.info("Соединение с клиентом, находящимся по адресу {}, установлено", clientSocket.getRemoteSocketAddress().toString());
            return clientSocket;
        } catch (IOException | IllegalBlockingModeException | IllegalArgumentException e) {
            throw new ConnectException("Ошибка соединения");
        }
    }


    private static Pair<String[], Integer> processArguments(String[] args) {
        try {

            if (args.length == 6) {
                String databaseHost = args[0];
                String databaseName = args[1];
                int dataBasePort = Integer.parseInt(args[2]);
                String databaseUsername = args[3];
                String userPass = args[4];
                String databaseAddress = "jdbc:postgresql://" + databaseHost + ":" + dataBasePort + "/" + databaseName;
                int port = Integer.parseInt(args[5]);

                if (dataBasePort <= 1024 || port <= 1024) {
                    throw new IllegalArgumentException("Указан недопустимый порт");
                }
                return new Pair<>(new String[]{databaseAddress, databaseUsername, userPass}, port);

            } else {
                throw new IllegalArgumentException("При запуске jar некорректно указаны аргументы (правильный вариант: java -jar <имя jar> <хост бд> <имя бд> <порт бд> <имя пользователя> <пароль> <порт сервера>");
            }
        } catch (NumberFormatException e) {
            logger.error("Порт должен быть целым числом");
            emergencyExit();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            emergencyExit();
        } catch (Exception e) {
            logger.error("Непредвиденная ошибка при расшифровке аргументов командной строки");
            emergencyExit();
        }
        return new Pair<>(new String[]{"", "", ""}, 8234);
    }

    private static void emergencyExit() {
        logger.error("Осуществляется аварийный выход из сервера");
        System.exit(1);
    }



    private static void addShutdownHook(DatabaseManager databaseManager, ExecutorService fixedThreadPool) {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    logger.info("Выполняются действия после сигнала о прекращении работы сервера");
                    databaseManager.closeConnectionWithDatabase();
                    fixedThreadPool.shutdown();
                }
                ));
    }

    public CommandWrapper getCommandWrapper() {
        return commandWrapper;
    }

}