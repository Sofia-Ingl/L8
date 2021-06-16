package client.bundles;

import java.util.ListResourceBundle;

public class gui_en_CA extends ListResourceBundle {
    private static final Object[][] contents = {

            {"CommandSuccess", "Success"},
            //{"AddSuccess", "Элемент добавлен в коллекцию!"},
            {"AddError", "Error while add command processing"},
            {"AddIfMaxError", "Error while max adding"},
            //{"AddIfMaxSuccess", "Максимальный элемент успешно добавлен"},
            //{"AddIfMaxNotSuccess", "Элемент не был максимальным, в коллекцию не добавлен"},
            {"ScriptError", "Error while script processing"},
            {"RegisterError", "Error while processing registration"},
            {"LoginError", "Error while logging in system"},
            {"RemoveError", "Error while removing"},
            {"RemoveGreaterError", "Error while removing greater"},
            {"RemoveByScreenwriterError", "Error while removing by screenwriter"},
            {"UpdateError", "Error while updating"},

            {"HelpMessage", "Help: \n" +
                    "At the bottom of the window there are buttons for interacting with the base." +
                    "To perform deletion or update actions, you must select a table row." +
                    "The \" Clear \"command removes from the database only those objects that belong to you. \n" +
                    "In the \" Visualization \"section, you will find a visual representation of the database objects."},
            //{"InfoMessage", "Информация о коллекции: \n Дата доступа: {0} \n Тип: {1} \n Размер: {2}"},

            {"idColumn", "Id"},
            {"nameColumn", "Name"},
            {"xColumn", "X"},
            {"yColumn", "Y"},
            {"creationDateColumn", "Creation date"},
            {"oscarsColumn", "Oscars"},
            {"gPalmsColumn", "Golden palms"},
            {"taglineColumn", "Tagline"},
            {"genreColumn", "Genre"},
            {"scrNameColumn", "Screenwriter"},
            {"scrHeightColumn", "Height"},
            {"eyeColorColumn", "Eye color"},
            {"nationalityColumn", "Nationality"},
            {"ownerColumn", "Owner"},

            {"addButton", "Add"},
            {"addIfMaxButton", "Add if max"},
            {"clearButton", "Clear"},
            {"executeScriptButton", "Execute script"},
            {"gPalmsFilterButton", "Filter greater than golden palms"},
            {"helpButton", "Help"},
            {"historyButton", "History"},
            {"infoButton", "Info"},
            {"removeByScreenwriterButton", "Remove by screenwriter"},
            {"removeByIdButton", "Remove by id"},
            {"removeGreaterButton", "Remove greater"},
            {"updateButton", "Update"},
            {"refreshButton", "Refresh"},

            {"ConnectionError", "Connection error"},
            {"Disconnected", "Disconnected..."},
            {"Connected", "Connected"},

            {"usernameLabel", "Login"},
            {"passwordLabel", "Password"},
            {"registerCheckBox", "Register"},
            {"signInButton", "Sign in"},

    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
