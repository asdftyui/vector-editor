package com.example.vectoreditor.model;

public class Ellipse extends GraphicObject{
    private String color;

    public String getColor() {
        return color;
    }

    @Override
    public String getProperty() {
        return getTitle() + ' ' + getX() + ' ' + getY() + ' ' + getHeight() + ' ' + getWidth() + ' ' + getSlope() + ' ' + getColor();
    }
}
