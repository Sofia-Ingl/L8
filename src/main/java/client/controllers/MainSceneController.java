package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class MainSceneController {

    @FXML
    private GridPane topPanel;
    @FXML
    private ComboBox languageChoiceBox;
    @FXML
    private Button refreshButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Tab tableTab;
    @FXML
    private TableView movieTable;
    @FXML
    private Tab visualTab;


    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn xColumn;
    @FXML
    private TableColumn yColumn;
    @FXML
    private TableColumn creationDateColumn;
    @FXML
    private TableColumn oscarsColumn;
    @FXML
    private TableColumn gPalmsColumn;
    @FXML
    private TableColumn taglineColumn;
    @FXML
    private TableColumn genreColumn;
    @FXML
    private TableColumn scrNameColumn;
    @FXML
    private TableColumn scrHeightColumn;
    @FXML
    private TableColumn eyeColorColumn;
    @FXML
    private TableColumn nationalityColumn;
    @FXML
    private TableColumn ownerColumn;


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
