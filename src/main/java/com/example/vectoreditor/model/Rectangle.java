package com.example.vectoreditor.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Map;

public class Rectangle extends javafx.scene.shape.Rectangle implements BasicFunction, Subject{
    private ArrayList<Observer> observers;
    private String title;

    public Rectangle(int title_num){
        super(300, 300, 200, 200);
        this.title = "layer" + title_num;
        this.observers = new ArrayList<>();
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
    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    @Override
    public void setColor(Color color) {
        setFill(color);
    }

    @Override
    public void setZOrder(boolean front) {
        if (front){
            toFront();
        } else {
            toBack();
        }
    }

    @Override
    public void moveObject(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
        notifyObservers();
    }

    @Override
    public void resizeObject(double deltaX, double deltaY, double height, double width) {
        moveObject(deltaX, deltaY);
        setHeight(height);
        setWidth(width);
        notifyObservers();
    }

    @Override
    public boolean isSelectObject(double pointX, double pointY) {
        if (contains(pointX, pointY)){
            notifyObservers();
            return true;
        }
        return false;
    }

    @Override
    public boolean isSelectObject(javafx.scene.shape.Rectangle rectangle) {
        Shape intersection = Shape.intersect(this, rectangle);

        if (intersection.getBoundsInLocal().getWidth() != -1 || intersection.getBoundsInLocal().getHeight() != -1) {
            // 교차된 영역이 비어있지 않으면 true를 반환
            notifyObservers();
            return true;
        }
        return false;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0){
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer o: observers){
            o.update(getAttribute());
        }
    }
}
