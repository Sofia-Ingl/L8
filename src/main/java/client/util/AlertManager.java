package client.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertManager {

    private static Localization localization;

    public static void setLocalization(Localization loc) {
        localization = loc;
    }

    private static void message(String title, String body, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(localization.getStringBinding(title));
        alert.setHeaderText(null);
        alert.setContentText(localization.getStringBinding(body));
        alert.showAndWait();
    }

    public static void displayError(String errorText) {
        message("Error", errorText, AlertType.ERROR);
    }
}
