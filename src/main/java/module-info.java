module com.example.vectoreditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vectoreditor to javafx.fxml;
    exports com.example.vectoreditor;
}