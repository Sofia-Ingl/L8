package client.controllers;

import client.Client;
import client.FXApp;
import client.util.AlertManager;
import client.util.Localization;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.Executors;

public class LoginSceneController extends Controller {


    private Client client;
    private FXApp app;
    private Localization localization;

    @FXML
    private ImageView image;
    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox registerCheckBox;
    @FXML
    private Button signInButton;
    @FXML
    private Label connectionLabel;

    @Override
    public void initialize() {

    }

    @Override
    public void setLocalization(Localization localization) {
        this.localization = localization;
        Font font = new Font("System", 18);
        usernameLabel.setText(localization.getStringBinding("usernameLabel"));
        usernameLabel.setFont(font);
        passwordLabel.setText(localization.getStringBinding("passwordLabel"));
        passwordLabel.setFont(font);
        signInButton.setText(localization.getStringBinding("signInButton"));
        registerCheckBox.setText(localization.getStringBinding("registerCheckBox"));
    }

    @FXML
    private void signInButtonOnAction() {
        if (client.getSocketChannel() == null || !client.getSocketChannel().isConnected()) {
            connectionLabel.textProperty().setValue(localization.getStringBinding("Disconnected"));
            connectionLabel.setTextFill(Color.rgb(30, 15, 220));
            AlertManager.message("CONNECTION ERROR", "Connection failed, reconnection in process", Alert.AlertType.ERROR);
            try {
                Thread.sleep(500);
                client.resetConnection();
            } catch (IOException | InterruptedException e) {
                AlertManager.message("CONNECTION ERROR", "Reconnection failed", Alert.AlertType.ERROR);
                return;
            }
        }
        if (client.processAuthorization(usernameField.getText(), passwordField.getText(), registerCheckBox.isSelected())) {
            app.setMainScene();
        } else {
            connectionLabel.textProperty().setValue(localization.getStringBinding("Connected"));
            connectionLabel.setTextFill(Color.BLACK);
        }
    }


    public void playAnimation() {
        RotateTransition rt = new RotateTransition(Duration.seconds(5), image);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setCycleCount(10);

        rt.play();

    }

    class AnimationTask implements Runnable {
        private RotateTransition rotateTransition;

        public AnimationTask(RotateTransition rt) {
            rotateTransition = rt;
        }

        @Override
        public void run() {
            rotateTransition.play();
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setApp(FXApp fxApp) {
        this.app = fxApp;
    }

    public void buttonHighlighted(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;");
    }

    public void buttonNormalized(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #ffbd75; -fx-border-color: #777571; -fx-border-width: 1;");
    }
}
