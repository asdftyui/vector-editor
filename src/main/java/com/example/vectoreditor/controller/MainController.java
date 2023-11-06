package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.ObjectHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private ToggleButton selectBtn;
    @FXML
    private Button lineBtn;
    @FXML
    private Button rectangleBtn;
    @FXML
    private Button ellipseBtn;
    @FXML
    private Button textBtn;
    @FXML
    private Button imageBtn;
    @FXML
    private TextField titleText;
    @FXML
    private TextField widthText;
    @FXML
    private TextField heightText;
    @FXML
    private ObjectHandler objectHandler;
    private MouseEventController mouseEventController;
    private PropertyWindowController propertyWindowController;
    @FXML
    private Button frontButton;
    @FXML
    private Button backButton;
    @FXML
    private ColorPicker color;
    @FXML
    private Button delButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        propertyWindowController = new PropertyWindowController(titleText, widthText, heightText, frontButton, backButton, color, delButton);
        objectHandler = new ObjectHandler(root, propertyWindowController);
        mouseEventController = new MouseEventController(root, objectHandler);
        rectangleBtn.setOnMouseClicked(e -> {objectHandler.createObject("Rectangle");});
        ellipseBtn.setOnMouseClicked(e -> {objectHandler.createObject("Ellipse");});
        mouseEventController.setMouseEvent(false);
        selectBtn.setOnAction(e -> {
            if (selectBtn.isSelected()){
                mouseEventController.setMouseEvent(true);
            } else{
                mouseEventController.setMouseEvent(false);
            }
        });
    }
}
