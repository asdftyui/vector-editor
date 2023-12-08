package com.example.vectoreditor.model;

import com.example.vectoreditor.controller.CurrentPropertyDisplay;
import com.example.vectoreditor.controller.PropertyWindowController;
import javafx.scene.layout.BorderPane;

public class ObjectFactory{
    public BasicFunction createObject(BorderPane root, PropertyWindowController propertyWindowController, String objectType, int num, String imagePath) {
        BasicFunction element;
        double canvasWidth = root.getWidth();
        double canvasHeight = root.getHeight();

        switch (objectType) {
            case "Rectangle":
                Rectangle rectangle = new Rectangle(num);
                root.getChildren().add(rectangle);
                new CurrentPropertyDisplay(rectangle, propertyWindowController);
                element = rectangle;
                break;
            case "Ellipse":
                Ellipse ellipse = new Ellipse(num);
                root.getChildren().add(ellipse);
                new CurrentPropertyDisplay(ellipse, propertyWindowController);
                element = ellipse;
                break;
            case "Line":
                Line line = new Line(num);
                root.getChildren().add(line);
                new CurrentPropertyDisplay(line, propertyWindowController);
                element = line;
                break;
            case "ImageM":
                if (imagePath != null && !imagePath.isEmpty()){
                    ImageM image = new ImageM(imagePath, num, canvasWidth*0.4, canvasHeight*0.4);
                    image.centerImageOnCanvas(canvasWidth, canvasHeight);
                    root.getChildren().add(image);
                    new CurrentPropertyDisplay(image, propertyWindowController);
                    element = image;
                } else {
                    element = null;
                }
                break;
            case "Text":
                Text text = new Text(num);
                root.getChildren().add(text);
                new CurrentPropertyDisplay(text, propertyWindowController);
                element = text;
                break;
            default:
                element = null;
                break;
        }
        return element;
    }
}
