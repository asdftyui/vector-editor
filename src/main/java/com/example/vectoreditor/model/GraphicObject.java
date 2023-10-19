package com.example.vectoreditor.model;

public abstract class GraphicObject {
    private String title;
    private double x;
    private double y;
    private double height;
    private double width;
    private double slope;

    public String getTitle() {
        return title;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getSlope() {
        return slope;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void move(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void resize(double height, double width){
        this.height = height;
        this.width = width;
    }

    public void rotate(double slope){
        this.slope = slope;
        // x, y 값 변화?
    }

    abstract public String getProperty();
}
