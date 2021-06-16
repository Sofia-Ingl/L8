package client.util;

import java.util.ResourceBundle;

public class Localization {

    private ResourceBundle resourceBundle;

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getStringBinding(String keyValue) {
        return resourceBundle.getString(keyValue);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
