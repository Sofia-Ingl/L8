package client.controllers;

import client.Client;
import client.util.AlertManager;
import client.util.Localization;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import shared.data.Color;
import shared.data.Country;
import shared.data.Movie;
import shared.data.MovieGenre;
import shared.serializable.User;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainSceneController {

    private Stage primaryStage;
    private Stage askStage;
    private AskSceneController askSceneController;
    private Client client;
    private User user;
    private Localization localization;
    private HashMap<String, Locale> sysLocales;

    @FXML
    private GridPane topPanel;
    @FXML
    private ComboBox<String> languageChoiceBox;
    @FXML
    private Button refreshButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Tab tableTab;
    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private Tab visualTab;


    @FXML
    private TableColumn<Movie, Integer> idColumn;
    @FXML
    private TableColumn<Movie, String> nameColumn;
    @FXML
    private TableColumn<Movie, Float> xColumn;
    @FXML
    private TableColumn<Movie, Integer> yColumn;
    @FXML
    private TableColumn<Movie, ZonedDateTime> creationDateColumn;
    @FXML
    private TableColumn<Movie, Integer> oscarsColumn;
    @FXML
    private TableColumn<Movie, Long> gPalmsColumn;
    @FXML
    private TableColumn<Movie, String> taglineColumn;
    @FXML
    private TableColumn<Movie, MovieGenre> genreColumn;
    @FXML
    private TableColumn<Movie, String> scrNameColumn;
    @FXML
    private TableColumn<Movie, Long> scrHeightColumn;
    @FXML
    private TableColumn<Movie, Color> eyeColorColumn;
    @FXML
    private TableColumn<Movie, Country> nationalityColumn;
    @FXML
    private TableColumn<Movie, String> ownerColumn;


    @FXML
    private Button addButton;
    @FXML
    private Button addIfMaxButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button gPalmsFilterButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button removeByScreenwriterButton;
    @FXML
    private Button removeByIdButton;
    @FXML
    private Button removeGreaterButton;
    @FXML
    private Button updateButton;

    public void initialize() {
        initTable();
        sysLocales = new HashMap<>();
        sysLocales.put("Русский", new Locale("ru", "RU"));
        sysLocales.put("English (Canada)", new Locale("en", "CA"));
        languageChoiceBox.setItems(FXCollections.observableArrayList(sysLocales.keySet()));
    }

    private void initTable() {
        idColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
        nameColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
        xColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCoordinates().getY()));
        oscarsColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getOscarsCount()));
        gPalmsColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getGoldenPalmCount()));
        taglineColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getTagline()));
        creationDateColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCreationDate()));
        genreColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getGenre()));
        scrNameColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getScreenwriter().getName()));
        scrHeightColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getScreenwriter().getHeight()));
        eyeColorColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getScreenwriter().getEyeColor()));
        nationalityColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getScreenwriter().getNationality()));
        ownerColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getOwner().getLogin()));
    }

    public void addButtonOnAction() {
        askStage.showAndWait();
        try {
            Movie movie = askSceneController.getPreparedMovie();
            movie.setOwner(user);
            processRequest("add", "", movie);

        } catch (IllegalStateException ignored) {
        }
    }


    public void addIfMaxButtonOnAction() {
        askStage.showAndWait();
        try {
            Movie movie = askSceneController.getPreparedMovie();
            movie.setOwner(user);
            processRequest("add_if_max", "", movie);

        } catch (IllegalStateException ignored) {
        }
    }

    public void updateButtonOnAction() {
        try {
            if (!movieTable.getSelectionModel().isEmpty()) {
                int id = movieTable.getSelectionModel().getSelectedItem().getId();
                askStage.showAndWait();
                Movie movie = askSceneController.getPreparedMovie();
                movie.setOwner(user);
                processRequest("update", String.valueOf(id), movie);
            } else {
                AlertManager.message("", "Object not chosen", Alert.AlertType.ERROR);
            }
        } catch (IllegalStateException ignored) {
        }

    }

    public void clearButtonOnAction() {
        processRequest("clear");
    }


    public void refreshButtonOnAction() {
        processRequest("show");
    }

    public void processRequest(String command, String stringArg, Movie objArg) {
        LinkedHashSet<Movie> set = client.processUserRequest(command, stringArg, objArg);
        if (set != null) {
            ObservableList<Movie> movieList = FXCollections.observableArrayList(set);
            movieTable.setItems(movieList);
            movieTable.getSelectionModel().clearSelection();
        }
    }

    public void processRequest(String command) {
        processRequest(command, "", null);
    }

    public void setAskStage(Stage askStage) {
        this.askStage = askStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAskSceneController(AskSceneController askSceneController) {
        this.askSceneController = askSceneController;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUser(User user) {
        this.user = user;
        usernameLabel.setText(user.getLogin());
    }

    public void removeByScreenwriterButtonOnAction() {
        if (!movieTable.getSelectionModel().isEmpty()) {
            String screenwriter = movieTable.getSelectionModel().getSelectedItem().getScreenwriter().getName();
            processRequest("remove_all_by_screenwriter", screenwriter, null);
        } else {
            AlertManager.message("", "Object not chosen", Alert.AlertType.ERROR);
        }
    }

    public void removeByIdButtonOnAction() {
        if (!movieTable.getSelectionModel().isEmpty()) {
            int id = movieTable.getSelectionModel().getSelectedItem().getId();
            processRequest("remove_by_id", String.valueOf(id), null);
        } else {
            AlertManager.message("", "Object not chosen", Alert.AlertType.ERROR);
        }
    }

    public void removeGreaterButtonOnAction() {
        try {
            askStage.showAndWait();
            Movie movie = askSceneController.getPreparedMovie();
            movie.setOwner(user);
            processRequest("remove_greater", null, movie);
        } catch (IllegalStateException ignored) {
        }

    }

    public void buttonHighlighted(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #E1D1B9;");
    }

    public void buttonNormalized(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;");
    }

    public void languageChoiceBoxOnAction() {

        localization.setResourceBundle(ResourceBundle.getBundle("client.bundles.gui", sysLocales.get(languageChoiceBox.getValue())));
        setLanguage();
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
        for (String localeName : sysLocales.keySet()) {
            if (sysLocales.get(localeName).equals(localization.getResourceBundle().getLocale()))
                languageChoiceBox.getSelectionModel().select(localeName);
        }
        if (languageChoiceBox.getSelectionModel().getSelectedItem().isEmpty())
            languageChoiceBox.getSelectionModel().selectFirst();


        setLanguage();

    }

    private void setLanguage() {
        idColumn.setText(localization.getStringBinding("idColumn"));
        nameColumn.setText(localization.getStringBinding("nameColumn"));
        xColumn.setText(localization.getStringBinding("xColumn"));
        yColumn.setText(localization.getStringBinding("yColumn"));
        creationDateColumn.setText(localization.getStringBinding("creationDateColumn"));
        oscarsColumn.setText(localization.getStringBinding("oscarsColumn"));
        gPalmsColumn.setText(localization.getStringBinding("gPalmsColumn"));
        taglineColumn.setText(localization.getStringBinding("taglineColumn"));
        genreColumn.setText(localization.getStringBinding("genreColumn"));
        scrNameColumn.setText(localization.getStringBinding("scrNameColumn"));
        scrHeightColumn.setText(localization.getStringBinding("scrHeightColumn"));
        eyeColorColumn.setText(localization.getStringBinding("eyeColorColumn"));
        nationalityColumn.setText(localization.getStringBinding("nationalityColumn"));
        ownerColumn.setText(localization.getStringBinding("ownerColumn"));


        addButton.setText(localization.getStringBinding("addButton"));
        addIfMaxButton.setText(localization.getStringBinding("addIfMaxButton"));
        clearButton.setText(localization.getStringBinding("clearButton"));
        executeScriptButton.setText(localization.getStringBinding("executeScriptButton"));
        gPalmsFilterButton.setText(localization.getStringBinding("gPalmsFilterButton"));
        helpButton.setText(localization.getStringBinding("helpButton"));
        historyButton.setText(localization.getStringBinding("historyButton"));
        infoButton.setText(localization.getStringBinding("infoButton"));
        removeByScreenwriterButton.setText(localization.getStringBinding("removeByScreenwriterButton"));
        removeByIdButton.setText(localization.getStringBinding("removeByIdButton"));
        removeGreaterButton.setText(localization.getStringBinding("removeGreaterButton"));
        updateButton.setText(localization.getStringBinding("updateButton"));
        refreshButton.setText(localization.getStringBinding("refreshButton"));
    }
}
