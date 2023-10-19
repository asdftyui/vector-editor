package com.example.vectoreditor.model;

public class Image extends GraphicObject{
    @Override
    public String getProperty() {
        return getTitle() + ' ' + getX() + ' ' + getY() + ' ' + getHeight() + ' ' + getWidth() + ' ' + getSlope();
    }
}
