package com.example.demo1;

import KeyReaderPackage.KeyReader;
import Movie.Movie;
import OMDB.OMDBApi;
import SQLite.SQLite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class HelloController {
    @FXML
    private Button btnSearch;
    @FXML
    private ChoiceBox categories;
    @FXML
    private TextField userInputNotYear;
    @FXML
    private TextField userInputYear;
    @FXML
    private TextArea result;
    @FXML
    private ImageView image;
    private Alert alert;
    private SQLite sqLite;
    private OMDBApi omdbApi;
    private KeyReader keyReader;
    public HelloController() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        keyReader = new KeyReader("OMDB");
        omdbApi = new OMDBApi(keyReader.getAPIKey());
        sqLite = new SQLite("movies");
        sqLite.createTable();

    }
    private void addMovie(Movie movie) {
        alert.setTitle("Add Movie");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to add the movie?");
        Optional<ButtonType> userAnswer = alert.showAndWait();
        if (userAnswer.isPresent() && userAnswer.get() == ButtonType.OK) {
            sqLite.addMovie(movie);
        }
    }
    private void moviePic(String poster) {
        // Här behövs det från JSON-object att man tar key-value "Poster"
        try {
            Image image1 = new Image(poster);
            image.setImage(image1);
        } catch (RuntimeException e) {

        }

    }
    private String userSearch(TextField textField) {
        try {
            return textField.getText();
        } catch (RuntimeException e) {
        }
        return null;
    }
    void setTextAlert(String title, String content) {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
    }

    private void searchMovie(String search) {
        // Sökningen skulle först ske via SQLite DB, därför denna ordningen, först kontroll mot
        // SQLite och sen efter kontroll mot API:et.
        // Om sqlite db ej hittar filmen som sökt på
        if (sqLite.getMovie(search) == null) {
            if (omdbApi.searchTitle(search) != null) { // om filmen finns i omdb API
                Movie movie1 = omdbApi.createMovie(omdbApi.searchTitle(search)); // Ny film sparas från omdb's API
                result.setText(movie1.toString()); // Information om film skrivs ut i TextArea
                addMovie(movie1); // Frågar om användaren vill lägga till filmen i DB
            } else {
                result.setText("The movie was not found");
            }
        } else { // Om sqlite hittade filmen, skall denna visas i TextArea
            result.setText(Arrays.toString(sqLite.getMovie(search)));
        }
    }
    private void searchActor(String search) throws SQLException {
        // Här går det enbart att söka via SQLite
        if (sqLite.getMovieActor(search) == null) {
            // Om actor ej hittas
            result.setText("The actor was not found");
        } else {
            // Om actor hittas, använd metod getActor för att skriva ut info om actor
            result.setText(sqLite.getMovieActor(search));
        }
    }
    private void searchDirector(String search) throws SQLException {
        // Här går det bara att söka via SQLite
        if (sqLite.getMovieDirector(search) == null) {
            result.setText("The director was not found");
        } else {
            result.setText(sqLite.getMovieDirector(search));
        }
    }
    private void searchGenre(String search) {
        result.setText(sqLite.getMovieGenre(search));
    }
    private void searchYear(String search) throws SQLException {
        // Här går det bara att söka via SQLite
        result.setText(sqLite.getMovieYear(search));
    }
    private void showAllMovieByYear(String search) {
        result.setText(sqLite.getAllMovieByYear(search));
    }

    // Här är det inte helt klart
    private void searchTitleAndYear(String search, String searchYear) throws SQLException {
        // Här går det med sökning från API och SQL
        // Men först skall det sökas efter i DB
        // Hämtar användarens sökning i TextField
        if (sqLite.getMovieTitleAndYear(search, searchYear).isEmpty()) {
            if (omdbApi.searchTitleAndYear(search, searchYear) != null) { // om filmen finns i omdb API
                Movie movie1 = omdbApi.createMovie(omdbApi.searchTitleAndYear(search, searchYear)); // Ny film sparas från omdb's API
                if(movie1.getTitle().isEmpty() || movie1.getTitle() == null) { // Detta betyder att film inte hittats
                    result.setText("The movie with that year was not found");
                    return;
                }
                moviePic(movie1.getPoster());
                result.setText(movie1.toString()); // Information om film skrivs ut i TextArea
                addMovie(movie1); // Frågar om användaren vill lägga till filmen i DB
            }
        } else { // Om sqlite hittade filmen, skall denna visas i TextArea
                result.setText(Arrays.toString(sqLite.getMovie(search, searchYear)));
        }

        if(sqLite.getMovieTitleAndYear(search,searchYear).isEmpty()) {

        }
    }
    private void showAllMoviesByTitle(String search) {
        // Här går det bara att söka via SQLite
        if (sqLite.getMovieTitles(search) == null) {
            result.setText("No movies with that title was found");
        } else {
            result.setText(sqLite.getMovieTitles(search));
        }
    }
    @FXML
    protected void onSearchClick() throws SQLException {
        String search = userSearch(userInputNotYear);
        if (search.isEmpty()) {
            setTextAlert("Missing search", "You have to type in something to search for");
            alert.showAndWait();
            return;
        }
            result.setVisible(true);
            String userChoiseBox = choiceBoxChoice();
            // Här bestämst hur sökningen skall genomföras genom att kontrollera vilket alternativ i choiceBoxen användaren valt
        switch (userChoiseBox) {
            // Uppdaterad SQL kod som fungerar
            case "Show all: Movie Titles":
            case "Search on: Movie Title":
                showAllMoviesByTitle(search);
                break;
                // Uppdaterad SQL kod som fungerar
            case "Show all: Movies by Actor":
            case "Search on: Actor":
                searchActor(search);
                break;
                // Uppdaterad SQL kod som fungerar
            case "Show all: Movies by Director":
            case "Search on: Director":
                searchDirector(search);
                break;
                // Uppdaterad SQL kod som fungerar
            case "Show all: Movies by Genre":
            case "Search on: Genre":
                searchGenre(search);
                break;
                // Uppdaterad SQL kod som fungerar
            case "Show all: Movies by Year":
                if(search.isEmpty()){
                    showAllMovieByYear(userSearch(userInputYear));
                }
                else {
                    showAllMovieByYear(search);
                }
            case "Search on: Year":
                searchYear(search);
                break;

            case "Search on: Movie Title and Year":
                String searchYear = userSearch(userInputYear);
                searchTitleAndYear(search, searchYear);
                break;
            default:
                setTextAlert("Note", "You have to search on something");
                alert.showAndWait();
                break;
        }
    }
    @FXML
    private String choiceBoxChoice() {
        return categories.getValue().toString();
    }
}
