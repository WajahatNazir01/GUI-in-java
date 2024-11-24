module com.example.demobmicalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demobmicalculator to javafx.fxml;
    exports com.example.demobmicalculator;
}