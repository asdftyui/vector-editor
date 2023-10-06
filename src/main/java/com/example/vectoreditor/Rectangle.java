package com.example.vectoreditor;

import javafx.scene.paint.Color;

public class Rectangle extends javafx.scene.shape.Rectangle implements Selectable {
    private String title;
    private double angle;
    private Color color;
    private boolean selected;
    public Rectangle(int num) {
        super(100, 100, 300, 300);
        this.title = "layer"+num;
        this.angle = 0;
        this.color = Color.BLACK;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setSelected() {
        this.selected = true;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void printProperties() {
        System.out.println("title:" + title);
        System.out.println("width: " + getWidth());
        System.out.println("height: " + getHeight());
        System.out.println("angle: " + angle);
        System.out.println("color: " + color);
    }
}
