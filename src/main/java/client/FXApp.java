package client;

import client.controllers.AskSceneController;
import client.controllers.LoginSceneController;
import client.controllers.MainSceneController;
import client.util.AlertManager;
import client.util.Localization;
import client.util.ScriptProcessor;
import client.util.UserElementGetter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.data.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FXApp extends Application {


    private static HashMap<String, Integer> allowedHosts;
    private static int validatedPort;
    private static String validatedHost;

    private Client client;
    private Stage primaryStage;
    private static Localization localization;

    public static void main(String[] args) {

        localization = new Localization();
        localization.setResourceBundle(ResourceBundle.getBundle("client.bundles.gui"));
        AlertManager.setLocalization(localization);
        loadConfigs();
        if (validateAndSetHostAndPort(args)) launch(args);
        else System.exit(0);
    }

    @Override
    public void init() {
        client = new Client(validatedHost, validatedPort);
        client.setScriptProcessor(new ScriptProcessor(new UserElementGetter()));
        try {
            client.setConnectionWithServer();
        } catch (IOException ignored) {
        }
    }


    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        setStageStyle(primaryStage);
        setAuthScene();


    }

    @Override
    public void stop() {
        client.stop();
    }

    private void setStageStyle(Stage stage) {
        stage.setTitle("Movie Application");
        InputStream iconStream = getClass().getResourceAsStream("/client/images/34.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
    }


    public void setAuthScene() {
        FXMLLoader loginWindowLoader = new FXMLLoader();
        loginWindowLoader.setLocation(getClass().getResource("/client/scenes/authWindow.fxml"));
        Parent loginWindowRootNode;
        try {

            loginWindowRootNode = loginWindowLoader.load();
            Scene loginWindowScene = new Scene(loginWindowRootNode);
            LoginSceneController loginWindowController = loginWindowLoader.getController();
            loginWindowController.setClient(client);
            loginWindowController.setApp(this);
            loginWindowController.setLocalization(localization);
            loginWindowController.playAnimation();
            primaryStage.setScene(loginWindowScene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMainScene() {

        try {

            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setLocation(getClass().getResource("/client/scenes/mainWindow.fxml"));
            Parent mainWindowRootNode = mainWindowLoader.load();
            Scene mainWindowScene = new Scene(mainWindowRootNode);
            MainSceneController mainWindowController = mainWindowLoader.getController();

            Stage askStage = new Stage();
            setStageStyle(askStage);
            FXMLLoader askSceneLoader = new FXMLLoader();
            askSceneLoader.setLocation(getClass().getResource("/client/scenes/askWindow.fxml"));
            Parent askWindowRoot = askSceneLoader.load();
            AskSceneController askSceneController = askSceneLoader.getController();

            askSceneController.setAskStage(askStage);

            mainWindowController.setPrimaryStage(primaryStage);
            mainWindowController.setAskStage(askStage);
            mainWindowController.setAskSceneController(askSceneController);
            mainWindowController.setClient(client);
            mainWindowController.setUser(client.getUser());
            mainWindowController.setLocalization(localization);
            mainWindowController.refreshButtonOnAction();

            Scene askScene = new Scene(askWindowRoot);
            askStage.setScene(askScene);
            askStage.setResizable(false);
            askStage.initModality(Modality.WINDOW_MODAL);
            askStage.initOwner(primaryStage);

            primaryStage.setScene(mainWindowScene);
            primaryStage.setMinWidth(mainWindowScene.getWidth());
            primaryStage.setMinHeight(mainWindowScene.getHeight());
            primaryStage.setResizable(true);
            runUpdateThread(mainWindowController);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runUpdateThread(MainSceneController controller) {
        Thread update = new Thread(() -> {
            try {
                while (true) {
                    HashSet<Movie> updated = client.processUserRequest("refresh", "", null);
                    if (updated != null) {

                        Platform.runLater(() -> controller.refreshData(updated));
                        Platform.runLater(() -> controller.refreshCanvas(false));
                    }
                    Thread.sleep(8000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Platform.exit();
            }
        });
        update.setDaemon(true);
        update.start();
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
                        throw new IllegalArgumentException("?????????????????? ???????? ???? ????????????????????????????");
                    }
                    port = Integer.parseInt(args[1]);
                }
                if (port <= 1024) {
                    throw new IllegalArgumentException("?????????????????? ???????? ???????????? ?????????????????? 1024");
                }

                validatedPort = port;
                validatedHost = host;

                return true;

            } else {
                throw new IllegalArgumentException("???? ???????????? ?????????????? ???????? ??/?????? ???????? ??????????????, ?? ???????????????? ?????????????????????? ????????????????????????");
            }
        } catch (NumberFormatException e) {
            System.out.println("???????? ???????????? ???????? ?????????? ????????????");
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

                System.out.println("???????? ?? ?????????????????????????? ???? ???????????? => localhost - ???????????????????????? ???????????????????? ????????");
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
                    System.out.println("???????? ???????????????????????? ?????????????? ????????????????");
                    allowedHosts = allowedHostsBuffer;
                } else {
                    System.out.println("???????? ???????????????????????? ?????????????????????? => localhost - ???????????????????????? ???????????????????? ????????");
                    allowedHosts = new HashMap<>();
                }

            }
        } catch (Exception e) {
            System.out.println("???????????????????????????? ???????????? ?????? ???????????? ?????? ???????????? ?????????? ???????????????????????? => localhost - ???????????????????????? ???????????????????? ????????");
            allowedHosts = new HashMap<>();
        }
    }

}
