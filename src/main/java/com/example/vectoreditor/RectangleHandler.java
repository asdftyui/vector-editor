package com.example.vectoreditor;

import javafx.scene.layout.BorderPane;

public class RectangleHandler {
    public void createRectangle(BorderPane root){
        Rectangle rectangle = new Rectangle(1);
        root.getChildren().add(rectangle);
    }

}

