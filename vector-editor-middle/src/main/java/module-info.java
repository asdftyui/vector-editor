module com.example.vectoreditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vectoreditor.controller to javafx.fxml;
    exports com.example.vectoreditor;
}