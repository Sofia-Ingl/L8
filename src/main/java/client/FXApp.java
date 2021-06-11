package client;

import client.controllers.LoginSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class FXApp extends Application {


    private static HashMap<String, Integer> allowedHosts;
    private static int validatedPort;
    private static String validatedHost;

    private Client client;
    private Stage primaryStage;

    public static void main(String[] args) {

        loadConfigs();
        if (validateAndSetHostAndPort(args)) launch(args);
        else System.exit(0);
    }

    @Override
    public void init() {
        client = new Client(validatedHost, validatedPort);
        try {
            client.setConnectionWithServer();
        } catch (IOException ignored) {}
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        setStageStyle();

        FXMLLoader loginWindowLoader = new FXMLLoader();
        loginWindowLoader.setLocation(getClass().getResource("/client/scenes/authWindow.fxml"));
        Parent loginWindowRootNode = loginWindowLoader.load();
        Scene loginWindowScene = new Scene(loginWindowRootNode);
        LoginSceneController loginWindowController = loginWindowLoader.getController();
        loginWindowController.setClient(client);
        loginWindowController.playAnimation();

        primaryStage.setScene(loginWindowScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setStageStyle() {
        primaryStage.setTitle("Movie Application");
        InputStream iconStream = getClass().getResourceAsStream("/client/images/34.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);
    }

    private static boolean validateAndSetHostAndPort(String[] args) {
        try {
            if (args.length > 1) {

                String hostToValidate = args[0].trim();
                String host = "localhost";
                int port;
                if (allowedHosts != null && allowedHosts.containsKey(hostToValidate)) {
                    port = allowedHosts.get(hostToValidate);
                } else {
                    if (!hostToValidate.equals("localhost")) {
                        throw new IllegalArgumentException("Введенный хост не поддерживается");
                    }
                    port = Integer.parseInt(args[1]);
                }
                if (port <= 1024) {
                    throw new IllegalArgumentException("Выбранный порт должен превышать 1024");
                }

                validatedPort = port;
                validatedHost = host;

                return true;

            } else {
                throw new IllegalArgumentException("Вы забыли указать хост и/или порт сервера, к которому собираетесь подключиться");
            }
        } catch (NumberFormatException e) {
            System.out.println("Порт должен быть целым числом");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }


    private static void loadConfigs() {
        try {
            Path path = Paths.get("config.txt");

            boolean dangerousPath = false;
            try {
                Path realPath = path.toRealPath();
                if (realPath.toString().length() > 3 && realPath.toString().trim().startsWith("/dev")) {
                    dangerousPath = true;
                }
            } catch (IOException e) {
                dangerousPath = true;
            }

            if (!path.toFile().exists() || dangerousPath) {

                System.out.println("Файл с конфигурацией не найден => localhost - единственный допустимый хост");
                allowedHosts = new HashMap<>();

            } else {

                HashMap<String, Integer> allowedHostsBuffer = new HashMap<>();
                boolean problems = false;
                try (Scanner configScanner = new Scanner(path)) {

                    String line;
                    while (configScanner.hasNextLine()) {
                        line = configScanner.nextLine();
                        String[] hostAndPort = (line + " ").split(" ", 2);
                        try {
                            int port = Integer.parseInt(hostAndPort[1].trim());
                            allowedHostsBuffer.put(hostAndPort[0].trim(), port);
                        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
                            problems = true;
                        }
                    }
                } catch (IOException e) {
                    problems = true;
                }

                if (!problems) {
                    System.out.println("Файл конфигурации успешно прочитан");
                    allowedHosts = allowedHostsBuffer;
                } else {
                    System.out.println("Файл конфигурации некорректен => localhost - единственный допустимый хост");
                    allowedHosts = new HashMap<>();
                }

            }
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка при поиске или чтении файла конфигурации => localhost - единственный допустимый хост");
            allowedHosts = new HashMap<>();
        }
    }

}
