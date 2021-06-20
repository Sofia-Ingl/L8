package client.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import shared.serializable.ServerResponse;

import java.util.List;

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

    private static void commandMessage(String title, String body) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(localization.getStringBinding(title));
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }

    public static void displayError(String errorText) {
        message("Error", errorText, AlertType.ERROR);
    }

    public static void displayInfo(String infoText) {
        message("Info", infoText, AlertType.INFORMATION);
    }


    public static void displayCommandResult(String command, ServerResponse response) {
        String body = localization.getStringBinding(response.getResponseToPrint());
        if (command.equals("history")) {
            String bodyArgs = localizeArgs(response.getResponseBodyArgs());
            body = body + "\n" + bodyArgs;
        }
        if (command.equals("info")) {
            System.out.println(response.getResponseBodyArgs().size());
            String type = response.getResponseBodyArgs().get(0);
            String size = response.getResponseBodyArgs().get(1);
            String initTime = response.getResponseBodyArgs().get(2);
            body = String.format(localization.getResourceBundle().getLocale(), body, type, size, initTime);
        }
        commandMessage(command, body);
    }

    private static String localizeArgs(List<String> args) {
        StringBuilder builder = new StringBuilder();
        for (String s:
             args) {
            builder.append(localization.getStringBinding(s)).append("\n");
        }
        return builder.toString();
    }
}
