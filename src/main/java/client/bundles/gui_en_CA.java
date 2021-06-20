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
            {"UserInsertionError", "User with the same name is already in db!"},
            {"LoginError", "Error while logging in system"},
            {"WrongNameOrPassError", "Wrong name or password!"},
            {"RemoveError", "Error while removing"},
            {"RemoveGreaterError", "Error while removing greater"},
            {"RemoveByScreenwriterError", "Error while removing by screenwriter"},
            {"UpdateError", "Error while updating"},

            {"HelpMessage", "Help: \n" +
                    "At the bottom of the window there are buttons for interacting with the base." +
                    "To perform deletion or update actions, you must select a table row." +
                    "The \" Clear \"command removes from the database only those objects that belong to you. \n" +
                    "In the \" Visualization \"section, you will find a visual representation of the database objects."},
            {"InfoText", "Collection info:\n Object type: %s \n Size: %s \n Init time: %s \n"},

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

            {"visualTab", "Visualization"},
            {"tableTab","Table"},

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

            {"WrongArgsError", "Forbidden args were provided. Check if every field is correct"},
            {"ChoiceError", "Object not chosen!"},

            {"History", "History (from new to old):"},
            {"add", "Add"},
            {"add_if_max", "Add if max"},
            {"clear", "Clear"},
            {"execute_script", "Execute script"},
            {"filter_greater_than_golden_palm_count", "Filter greater than golden palms"},
            {"help", "Help"},
            {"history", "History"},
            {"info", "Info"},
            {"remove_all_by_screenwriter", "Remove by screenwriter"},
            {"remove_by_id", "Remove by id"},
            {"remove_greater", "Remove greater"},
            {"update", "Update"},
            {"refresh", "Refresh"},

            {"AccessError", "Attempt to update movie that does not belong to user!"},
            {"Error", "Error!"},
            {"Info", "Info"},
            {"NewTrial", "Pls, make another trial to execute command"}

    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
