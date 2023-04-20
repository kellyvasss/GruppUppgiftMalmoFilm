package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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


    public HelloController() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
    }
    private void addMovie() {
        alert.setTitle("Add Movie.Movie");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to add the movie?");
        Optional<ButtonType> userAnswer = alert.showAndWait();
        if (userAnswer.isPresent() && userAnswer.get() == ButtonType.OK) {
            // Här behövs SQLite.SQLite för att lägga till filmen
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
        } return null;
    }
    @FXML
    protected void onSearchClick() {
        userInputYear.setVisible(true);
        userInputNotYear.setVisible(true);
        result.setVisible(true);
        String search = userSearch(userInputNotYear);
        if (!search.isEmpty()) {
            userInputYear.setVisible(true);
            userInputNotYear.setVisible(true);
            result.setVisible(true);

            String userChoiseBox = choiceBoxChoice();

            switch (userChoiseBox) {
                case "Search on: Movie Title":


                    break;
                case "Search on: Actor":

                    break;
                case "Search on: Director":

                    break;
                case "Search on: Genre":

                    break;
                case "Search on: Year":

                    break;
                case "Search on: Movie Title and Year":

                    break;
                case "Show all: Movie Titles":

                    break;
                case "Show all: Movies by Actor":

                    break;
                case "Show all: Movies by Director":

                    break;
                case "Show all: Movies by Genre":

                    break;
                case "Show all: Movies by Year":

                    break;
                default:
                    alert.setTitle("Note");
                    alert.setHeaderText(null);
                    alert.setContentText("You have to search on something.");
            }
        moviePic();
    }

}@FXML
    private String choiceBoxChoice() {
        return categories.getValue().toString();
    }

}
