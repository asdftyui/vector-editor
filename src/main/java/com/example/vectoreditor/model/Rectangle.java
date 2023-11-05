package com.example.vectoreditor.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.Map;

public class Rectangle extends javafx.scene.shape.Rectangle implements BasicFunction{
    private String title;

    public Rectangle(int title_num){
        super(300, 300, 200, 200);
        this.title = "layer" + title_num;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public Map<String, String> getAttribute() {
        Color color = (Color) getFill();

        Map<String, String> attribute = Map.of(
                "title", getTitle(),
                "x", Double.toString(getX()),
                "y", Double.toString(getY()),
                "height", Double.toString(getHeight()),
                "width", Double.toString(getWidth()),
                "color", "#" + Integer.toHexString((int) (color.getRed() * 255))
                        + Integer.toHexString((int) (color.getGreen() * 255))
                        + Integer.toHexString((int) (color.getBlue() * 255))
        );

        return attribute;
    }

    @Override
    public void moveObject(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
    }

    @Override
    public void resizeObject(double deltaX, double deltaY, double height, double width) {
        moveObject(deltaX, deltaY);
        setHeight(height);
        setWidth(width);
    }

    @Override
    public boolean isSelectObject(double pointX, double pointY) {
        if (contains(pointX, pointY)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isSelectObject(javafx.scene.shape.Rectangle rectangle) {
        Shape intersection = Shape.intersect(this, rectangle);

        if (intersection.getBoundsInLocal().getWidth() != -1 || intersection.getBoundsInLocal().getHeight() != -1) {
            // 교차된 영역이 비어있지 않으면 true를 반환
            return true;
        }
        return false;
    }
}
