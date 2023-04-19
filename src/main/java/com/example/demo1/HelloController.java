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
    @FXML
    protected void onSearchClick() {
        userInputYear.setVisible(true);
        userInputNotYear.setVisible(true);
        result.setVisible(true);
        moviePic();
    }
}