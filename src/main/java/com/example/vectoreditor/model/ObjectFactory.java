package com.example.vectoreditor.model;

import com.example.vectoreditor.controller.CurrentPropertyDisplay;
import com.example.vectoreditor.controller.PropertyWindowController;
import javafx.scene.layout.BorderPane;

public class ObjectFactory{
    private ObjectHandler objectHandler;
    private PropertyWindowController propertyWindowController;
    private BorderPane root;

    public ObjectFactory(ObjectHandler objectHandler, BorderPane root, PropertyWindowController propertyWindowController) {
        this.objectHandler = objectHandler;
        this.propertyWindowController = propertyWindowController;
        this.root = root;
    }

    public void createRectangle() {
        Rectangle rectangle = new Rectangle(objectHandler.getElementSize()+1);
        root.getChildren().add(rectangle);
        new CurrentPropertyDisplay(rectangle, propertyWindowController);
        objectHandler.addElement(rectangle);
    }

    public void createEllipse() {
        Ellipse ellipse = new Ellipse(objectHandler.getElementSize()+1);
        root.getChildren().add(ellipse);
        new CurrentPropertyDisplay(ellipse, propertyWindowController);
        objectHandler.addElement(ellipse);
    }

    public void createLine() {
        Line line = new Line(objectHandler.getElementSize()+1);
        root.getChildren().add(line);
        new CurrentPropertyDisplay(line, propertyWindowController);
        objectHandler.addElement(line);
    }

    public void createImageM(String imagePath) {
        double canvasWidth = root.getWidth();
        double canvasHeight = root.getHeight();
        if (imagePath != null && !imagePath.isEmpty()){
            ImageM image = new ImageM(imagePath, objectHandler.getElementSize()+1, canvasWidth*0.4, canvasHeight*0.4);
            image.centerImageOnCanvas(canvasWidth, canvasHeight);
            root.getChildren().add(image);
            new CurrentPropertyDisplay(image, propertyWindowController);
            objectHandler.addElement(image);
        }
    }

    public void createText() {
        Text text = new Text(objectHandler.getElementSize()+1);
        root.getChildren().add(text);
        new CurrentPropertyDisplay(text, propertyWindowController);
        objectHandler.addElement(text);
    }
}
