package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.ObjectFactory;
import com.example.vectoreditor.model.ObjectHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;
import java.io.File;

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
    @FXML
    private Button frontButton;
    @FXML
    private Button backButton;
    @FXML
    private ColorPicker color;
    @FXML
    private TextField textv;
    @FXML
    private Slider fontsize;
    @FXML
    private ComboBox<String> fontkind;
    @FXML
    private Button delButton;
    private MouseEventController mouseEventController;
    private PropertyWindowController propertyWindowController;
    private PropertyWindowEventController propertyWindowEventController;
    private ObjectFactory objectFactory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	fontkind.getItems().addAll("Arial", "Verdana", "Times New Roman", "Comic Sans MS");
        propertyWindowController = new PropertyWindowController(titleText, widthText, heightText, color, textv, fontsize, fontkind);
        objectHandler = new ObjectHandler(root);

        objectFactory = new ObjectFactory(objectHandler, root, propertyWindowController);
        mouseEventController = new MouseEventController(root, objectHandler);
        propertyWindowEventController = new PropertyWindowEventController(objectHandler, titleText, widthText, heightText, frontButton, backButton, color, delButton, textv, fontsize, fontkind);

        rectangleBtn.setOnMouseClicked(e -> {objectFactory.createRectangle();});
        ellipseBtn.setOnMouseClicked(e -> {objectFactory.createEllipse();});
        lineBtn.setOnMouseClicked(e -> {objectFactory.createLine();});
        imageBtn.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
            if (selectedFile != null) {
                String imagePath = selectedFile.getAbsolutePath();
                objectFactory.createImageM(imagePath);
            }
        });
        textBtn.setOnMouseClicked(e -> {objectFactory.createText();});
        
        mouseEventController.setMouseEvent(false);
        selectBtn.setOnAction(e -> {
            if (selectBtn.isSelected()){
                mouseEventController.setMouseEvent(true);
            } else{
                mouseEventController.setMouseEvent(false);
            }
        });
        propertyWindowEventController.setEventHandler();
    }
}
