package com.example.vectoreditor.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.Map;

public interface BasicFunction {
    Map<String, String> getAttribute();
    void moveObject(double x, double y);
    void resizeObject(double deltaX, double deltaY, double height, double width);
    boolean isSelectObject(double pointX, double pointY);
    boolean isSelectObject(javafx.scene.shape.Rectangle rectangle);
}
