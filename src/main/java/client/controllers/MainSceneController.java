package client.controllers;

import client.Client;
import client.util.AlertManager;
import client.util.Localization;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.data.Color;
import shared.data.Country;
import shared.data.Movie;
import shared.data.MovieGenre;
import shared.serializable.Pair;
import shared.serializable.User;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.*;

public class MainSceneController {

    public TabPane tabPane;
    public VBox vBox;
    private Stage primaryStage;
    private Stage askStage;
    private AskSceneController askSceneController;
    private Client client;
    private User user;
    private Localization localization;
    private Map<String, Locale> sysLocales;
    private Map<String, javafx.scene.paint.Color> userColorMap;
    private FileChooser fileChooser;
    private Map<Shape, Integer> shapeMap;
    private Map<Integer, Text> textMap;
    private Random random;
    Shape prevClicked = null;
    javafx.scene.paint.Color prevColor;

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
    private AnchorPane visualPane;


    @FXML
    private TableColumn<Movie, Integer> idColumn;
    @FXML
    private TableColumn<Movie, String> nameColumn;
    @FXML
    private TableColumn<Movie, Float> xColumn;
    @FXML
    private TableColumn<Movie, Integer> yColumn;
    @FXML
    private TableColumn<Movie, String> creationDateColumn;
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
        shapeMap = new HashMap<>();
        textMap = new HashMap<>();
        userColorMap = new HashMap<>();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        random = new Random(17);

    }

    private void initTable() {
        idColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
        nameColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
        xColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCoordinates().getY()));
        oscarsColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getOscarsCount()));
        gPalmsColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getGoldenPalmCount()));
        taglineColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getTagline()));
        creationDateColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCreationDateString()));
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
                Movie m = movieTable.getSelectionModel().getSelectedItem();
                int id = m.getId();
                askSceneController.prepareFieldsForUpdate(m);
                askStage.showAndWait();
                Movie movie = askSceneController.getPreparedMovie();
                movie.setOwner(user);
                processRequest("update", String.valueOf(id), movie);
            } else {
                AlertManager.displayError("ChoiceError");
            }
        } catch (IllegalStateException ignored) {
        }

    }

    public void clearButtonOnAction() {
        processRequest("clear");
    }


    public void refreshButtonOnAction() {
        processRequest("refresh");
    }

    public void refreshCanvas(boolean canvasIsChosen) {

        shapeMap.keySet().forEach(shape -> visualPane.getChildren().remove(shape));
        textMap.values().forEach(text -> visualPane.getChildren().remove(text));
        shapeMap.clear();
        textMap.clear();

        for (Movie m : movieTable.getItems().sorted(Comparator.reverseOrder())) {

            if (!userColorMap.containsKey(m.getOwner().getLogin())) {
                userColorMap.put(m.getOwner().getLogin(), javafx.scene.paint.Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
            }

            int size = Math.min(m.getOscarsCount() * 15, 250);

            Circle movieCircle = new Circle(size, userColorMap.get(m.getOwner().getLogin()));

            double xTranslate = m.getCoordinates().getX()*4;
            double yTranslate = m.getCoordinates().getY()*1.5;

            if (!canvasIsChosen) {
                movieCircle.setTranslateX(xTranslate);
                movieCircle.setTranslateY(yTranslate);
            }


            movieCircle.setOnMouseClicked(this::shapeOnMouseClicked);

            Text movieText = new Text(String.valueOf(m.getId()));
            movieText.setFont(Font.font(size / 2));
            movieText.setOnMouseClicked(movieCircle::fireEvent);
            movieText.setFill(userColorMap.get(m.getOwner().getLogin()).darker().darker());
            movieText.translateXProperty().bind(movieCircle.translateXProperty().subtract(movieText.getLayoutBounds().getWidth() / 2));
            movieText.translateYProperty().bind(movieCircle.translateYProperty().add(movieText.getLayoutBounds().getHeight() / 4));

            visualPane.getChildren().add(movieCircle);
            visualPane.getChildren().add(movieText);

            shapeMap.put(movieCircle, m.getId());
            textMap.put(m.getId(), movieText);

            if (canvasIsChosen) {

                TranslateTransition circleTransition = getNodeTranslateTransition(movieCircle, xTranslate, yTranslate);
                circleTransition.play();
            }

            ScaleTransition circleTransition = getNodeScaleTransition(movieCircle);
            ScaleTransition textTransition = getNodeScaleTransition(movieText);

            circleTransition.play();
            textTransition.play();


        }
    }

    private TranslateTransition getNodeTranslateTransition(Node node, double transX, double transY) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), node);
        translateTransition.setByX(transX);
        translateTransition.setByY(transY);
        translateTransition.setAutoReverse(false);
        return translateTransition;
    }

    private ScaleTransition getNodeScaleTransition(Node node) {
        ScaleTransition objTransition = new ScaleTransition(Duration.millis(1000), node);
        objTransition.setCycleCount(10);
        objTransition.setFromX(0.75);
        objTransition.setToX(1);
        objTransition.setFromY(0.75);
        objTransition.setToY(1);
        objTransition.setAutoReverse(true);
        return objTransition;
    }

    private void shapeOnMouseClicked(MouseEvent mouseEvent) {

        Shape shape = (Shape) mouseEvent.getSource();
        int id = shapeMap.get(shape);
        for (Movie m :
                movieTable.getItems()) {
            if (m.getId() == id) {
                movieTable.getSelectionModel().select(m);
                break;
            }
        }

        if (prevClicked != null) {
            prevClicked.setFill(prevColor);
        }
        prevClicked = shape;
        prevColor = (javafx.scene.paint.Color) shape.getFill();
        shape.setFill(prevColor.brighter());

    }

    public void processRequest(String command, String stringArg, Movie objArg) {
        LinkedHashSet<Movie> set = client.processUserRequest(command, stringArg, objArg);
        if (set != null) {
            ObservableList<Movie> movieList = FXCollections.observableArrayList(set);
            movieTable.setItems(movieList);
            movieTable.getSelectionModel().clearSelection();
            refreshCanvas(false);
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
            AlertManager.displayError("ChoiceError");
        }
    }

    public void removeByIdButtonOnAction() {
        if (!movieTable.getSelectionModel().isEmpty()) {
            int id = movieTable.getSelectionModel().getSelectedItem().getId();
            processRequest("remove_by_id", String.valueOf(id), null);
        } else {
            AlertManager.displayError("ChoiceError");
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

        visualTab.setText(localization.getStringBinding("visualTab"));
        tableTab.setText(localization.getStringBinding("tableTab"));

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

    public void visualTabOnSelection() {
        refreshCanvas(true);
    }

    public void executeScriptButtonOnAction() {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) return;
        Pair<Boolean, HashSet<Movie>> result = client.processScript(selectedFile.getPath());
        if (result.getFirst()) {

            ObservableList<Movie> movieList = FXCollections.observableArrayList(result.getSecond());
            movieTable.setItems(movieList);
            movieTable.getSelectionModel().clearSelection();
            refreshCanvas(false);

        }
    }

    public void gPalmsFilterButtonOnAction(ActionEvent actionEvent) {
    }

    public void helpButtonOnAction() {
        processRequest("help");
    }

    public void historyButtonOnAction() {
        processRequest("history");
    }

    public void infoOnAction() {
        processRequest("info");
    }
}
