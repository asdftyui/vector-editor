package com.example.vectoreditor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private Button rectangle_btn;
    private RectangleHandler rectangleHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rectangleHandler = new RectangleHandler();
        rectangle_btn.setOnMouseClicked(e -> rectangleHandler.createObject(root));
    }
}