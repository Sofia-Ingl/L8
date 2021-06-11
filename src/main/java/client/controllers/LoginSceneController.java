package client.controllers;

import client.Client;
import client.FXApp;
import client.util.AlertManager;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LoginSceneController {

    private Client client;
    private FXApp app;

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


    @FXML
    private void signInButtonOnAction() {
        if (client.processAuthorization(usernameField.getText(), passwordField.getText(), registerCheckBox.isSelected())) {
            app.setMainScene();
        } else if (!client.isConnected()) {
            connectionLabel.textProperty().setValue("Disconnected...");
            connectionLabel.setTextFill(Color.rgb(30, 15, 220));
        } else {
            connectionLabel.textProperty().setValue("Connected");
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

    public void setClient(Client client) {
        this.client = client;
    }

    public void setApp(FXApp fxApp) {
        this.app = fxApp;
    }
}
