package client.controllers;

import client.util.AlertManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import shared.data.*;

public class AskSceneController {

    private Stage askStage;

    @FXML
    private TextField movieNameField;
    @FXML
    private TextField coordXField;
    @FXML
    private TextField coordYField;
    @FXML
    private TextField oscarsField;
    @FXML
    private TextField gPalmsField;
    @FXML
    private TextField taglineField;
    @FXML
    private ComboBox<MovieGenre> genreField;
    @FXML
    private TextField scrNameField;
    @FXML
    private TextField scrHeightField;
    @FXML
    private ComboBox<Color> eyeColorField;
    @FXML
    private ComboBox<Country> nationalityField;

    Movie freshlyBakedMovie;

    public void initialize() {
        genreField.setItems(FXCollections.observableArrayList(MovieGenre.values()));
        eyeColorField.setItems(FXCollections.observableArrayList(Color.values()));
        nationalityField.setItems(FXCollections.observableArrayList(Country.values()));
    }

    public void enterButtonOnAction() {
        try {
            freshlyBakedMovie = new Movie(getMovieName(), getCoordinates(), getOscars(), getPalms(), getTagline(),
                    genreField.getValue(), new Person(getScreenwriterName(), getHeight(), eyeColorField.getValue(),
                    nationalityField.getValue()));
            clearFields();
            askStage.close();
        } catch (IllegalArgumentException e) {
            AlertManager.message("WRONG ARGUMENTS", "Check if every field is correct", Alert.AlertType.ERROR);
        }
    }

    private String getMovieName() throws IllegalArgumentException {
        if (movieNameField.getText().equals("")) throw new IllegalArgumentException();
        return movieNameField.getText().trim();
    }

    private Coordinates getCoordinates() throws IllegalArgumentException {
        try {
            float X = Float.parseFloat(coordXField.getText().trim());
            int Y = Integer.parseInt(coordYField.getText().trim());
            if (X > 326 || Y > 281) throw new IllegalArgumentException();
            return new Coordinates(X, Y);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    private int getOscars() throws IllegalArgumentException {
        try {
            int oscars = Integer.parseInt(oscarsField.getText().trim());
            if (oscars < 1 || oscars > 20) throw new IllegalArgumentException();
            return oscars;
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    private long getPalms() throws IllegalArgumentException {
        try {
            long palms = Long.parseLong(gPalmsField.getText().trim());
            if (palms < 1) throw new IllegalArgumentException();
            return palms;
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    private String getTagline() {
        return taglineField.getText().trim();
    }

    private String getScreenwriterName() throws IllegalArgumentException {
        if (scrNameField.getText().equals("")) throw new IllegalArgumentException();
        return scrNameField.getText().trim();
    }

    private long getHeight() throws IllegalArgumentException {
        try {
            long height = Long.parseLong(scrHeightField.getText().trim());
            if (height < 10 || height > 300) throw new IllegalArgumentException();
            return height;
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    public Movie getPreparedMovie() {
        Movie movie = freshlyBakedMovie;
        if (movie == null) throw new IllegalStateException();
        freshlyBakedMovie = null;
        return movie;
    }

    public void prepareFieldsForUpdate(Movie m) {
        movieNameField.setText(m.getName());
        coordXField.setText(String.valueOf(m.getCoordinates().getX()));
        coordYField.setText(String.valueOf(m.getCoordinates().getY()));
        oscarsField.setText(String.valueOf(m.getOscarsCount()));
        gPalmsField.setText(String.valueOf(m.getGoldenPalmCount()));
        taglineField.setText(m.getTagline());
        genreField.setValue(m.getGenre());
        scrNameField.setText(m.getScreenwriter().getName());
        scrHeightField.setText(String.valueOf(m.getScreenwriter().getHeight()));
        eyeColorField.setValue(m.getScreenwriter().getEyeColor());
        nationalityField.setValue(m.getScreenwriter().getNationality());
    }

    public void clearFields() {
        movieNameField.clear();
        coordXField.clear();
        coordYField.clear();
        oscarsField.clear();
        gPalmsField.clear();
        taglineField.clear();
        scrNameField.clear();
        scrHeightField.clear();

        genreField.setValue(null);
        eyeColorField.setValue(null);
        nationalityField.setValue(null);
    }

    public void setAskStage(Stage askStage) {
        this.askStage = askStage;
    }

    public void buttonHighlighted(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #E1D1B9;");
    }

    public void buttonNormalized(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;");
    }
}
