package com.example.vectoreditor.model;

public class Text extends GraphicObject{

    private String detail;
    private double size;
    private String font;
    private String color;


    public String getDetail() {
        return detail;
    }

    public double getSize() {
        return size;
    }

    public String getFont() {
        return font;
    }

    public String getColor() {
        return color;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getProperty() {
        return getTitle() + ' ' + getDetail() + ' ' + getX() + ' ' + getY() + ' ' + getSize() + ' ' + getFont() + ' ' + getColor();
    }
}
