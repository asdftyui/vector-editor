package com.example.vectoreditor.model;

public class Rectangle extends GraphicObject{
    private String color;

    public Rectangle(){

    }

    public String getColor() {
        return color;
    }

    @Override
    public String getProperty() {
        return getTitle() + ' ' + getX() + ' ' + getY() + ' ' + getHeight() + ' ' + getWidth() + ' ' + getSlope() + ' ' + getColor();
    }
}
