module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    //exports OMDB;
    //opens OMDB to javafx.fxml;


    exports OMDB; // lägger till export av paketet OMDB
}

