package com.example.vectoreditor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

        rectangle_btn.setOnMouseClicked(e -> rectangleHandler.createRectangle(root));
        root.setOnMouseClicked(e -> {
            Selectable clickedObject = findClickedObject(root, e.getX(), e.getY());

            if (clickedObject != null){
                clickedObject.printProperties();
            }
        });
    }

    public Selectable findClickedObject(Parent root, double x, double y) {
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof Selectable && node.contains(node.sceneToLocal(x, y))) {
                return (Selectable) node;
            }
        }
        return null;
    }
}