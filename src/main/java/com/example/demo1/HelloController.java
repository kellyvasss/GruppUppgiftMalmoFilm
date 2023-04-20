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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private Movie movie;

    public HelloController() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        sqLite = new SQLite("Movie DB");
        keyReader = new KeyReader("OMDB");
        omdbApi = new OMDBApi(keyReader.getAPIKey());

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
    private void moviePic() {
        // Här behövs det från JSON-object att man tar key-value "Poster"
        Image image1 = new Image("https://m.media-amazon.com/images/M/MV5BZGM5NjliODgtODVlOS00OWZmLWIzYzMtMTI2OWIzMTM1ZGRhXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg");
        image.setImage(image1);
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
            result.setText(sqLite.getMovie(search));
        }
    }
    private void searchActor(String search) {
        // Här går det enbart att söka via SQLite
        if (sqLite.getActor(search) == null) {
            // Om actor ej hittas
            result.setText("The actor was not found");
        } else {
            // Om actor hittas, använd metod getActor för att skriva ut info om actor
            result.setText(sqLite.getActor(search));
        }
    }
    private void searchDirector(String search) {
        // Här går det bara att söka via SQLite
        if (sqLite.getDirector(search) == null) {
            result.setText("The director was not found");
        } else {
            result.setText(sqLite.getDirector(search));
        }
    }
    private void searchGenre(String search) {
        // Här går det bara att söka via SQLite
        if (sqLite.getGenre(search) == null) {
            result.setText("The genre was not found");
        } else {
            result.setText(sqLite.getGenre(search));
        }
    }
    private void searchYear(String search) {
        // Här går det bara att söka via SQLite
        if (sqLite.getYear(search) == null) {
            result.setText("The year was not found");
        } else {
            result.setText(sqLite.getYear(search));
        }
    }
    private void searchTitleAndYear(String search, String searchYear) {
        // Här går det med sökning från API och SQL
        // Men först skall det sökas efter i DB
        // Hämtar användarens sökning i TextField
        if (sqLite.getMovie(search, searchYear) == null) {
            if (omdbApi.searchTitleAndYear(search, searchYear) != null) { // om filmen finns i omdb API
                Movie movie1 = omdbApi.createMovie(omdbApi.searchTitleAndYear(search)); // Ny film sparas från omdb's API
                result.setText(movie1.toString()); // Information om film skrivs ut i TextArea
                addMovie(movie1); // Frågar om användaren vill lägga till filmen i DB
            } else {
                result.setText("The movie with that year was not found");
            }
        } else { // Om sqlite hittade filmen, skall denna visas i TextArea
            result.setText(sqLite.getMovie(search, searchYear));
        }
    }
    private void showAllMoviesByTitle(String search) {
        // Här går det bara att söka via SQLite
        if (sqLite.getMovie(search) == null) {
            result.setText("No movies with that title was found");
        } else {
            result.setText(sqLite.getMovie(search));
        }
    }
    @FXML
    protected void onSearchClick() {
        String search = userSearch(userInputNotYear);
        if (!search.isEmpty()) {
            setTextAlert("Missing search", "You have to type in something to search for");
            alert.showAndWait();
            return;
        }
            result.setVisible(true);
            String userChoiseBox = choiceBoxChoice();
            // Här bestämst hur sökningen skall genomföras genom att kontrollera vilket alternativ i choiceBoxen användaren valt
        switch (userChoiseBox) {
            case "Search on: Movie Title":
                searchMovie(search);
                break;
            case "Show all: Movies by Actor":
            case "Search on: Actor":
                searchActor(search);
                break;
            case "Show all: Movies by Director":
            case "Search on: Director":
                searchDirector(search);
                break;
            case "Show all: Movies by Genre":
            case "Search on: Genre":
                searchGenre(search);
                break;
            case "Show all: Movies by Year":
            case "Search on: Year":
                searchYear(search);
                break;
            case "Search on: Movie Title and Year":
                String searchYear = userSearch(userInputYear);
                searchTitleAndYear(search, searchYear);
                break;
            case "Show all: Movie Titles":
                showAllMoviesByTitle(search);
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
