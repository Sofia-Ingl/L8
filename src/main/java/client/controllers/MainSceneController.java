package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import shared.data.Color;
import shared.data.Country;
import shared.data.Movie;
import shared.data.MovieGenre;

import java.time.ZonedDateTime;

public class MainSceneController {

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
    private TableColumn<Movie, Integer> scrHeightColumn;
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


}
