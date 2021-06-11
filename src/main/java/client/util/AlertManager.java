package client.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertManager {

    public static void message(String title, String body, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }
}
