package com.example.vectoreditor;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class RectangleHandler {
    public void createObject(BorderPane root){
        Rectangle rectangle = new Rectangle(1);
        DragResizable.makeDragResizable((Node) rectangle);
        root.getChildren().add(rectangle);
    }

}

