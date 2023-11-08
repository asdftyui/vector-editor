package com.example.vectoreditor.model;

import com.example.vectoreditor.controller.Observer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Map;

public class Line extends javafx.scene.shape.Line implements BasicFunction, Subject {
    private ArrayList<Observer> observers;
    private String title;

    public Line(int title_num){
        super(300, 300, 500, 500);
        this.title = "layer" + title_num;
        this.observers = new ArrayList<>();
    }

    public String getTitle(){
        return title;
    }

    @Override
    public Map<String, String> getAttribute() {
        Color color = (Color) getStroke();

        Map<String, String> attribute = Map.of(
                "title", getTitle(),
                "x", Double.toString(getStartX()),
                "y", Double.toString(getStartY()),
                "height", Double.toString(getEndY() - getStartY()),
                "width", Double.toString(getEndX() - getStartX()),
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
        setStroke(color);
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
        setStartX(getStartX() + x);
        setStartY(getStartY() + y);
        setEndX(getEndX()+x);
        setEndY(getEndY()+y);
        notifyObservers();
    }

    @Override
    public void resizeObject(double deltaX, double deltaY, double height, double width) {
        if (width < 0){
            setEndX(deltaX);
            setEndY(deltaY);
        } else {
            setStartX(deltaX);
            setStartY(deltaY);
        }
        notifyObservers();
    }

    @Override
    public boolean isSelectObject(double pointX, double pointY) {
        double x = pointX;
        double y = pointY;
        double height = getEndY() - getStartY();
        double width = getEndX() - getStartX();
        double minX = getStartX();
        double minY = getStartY();

        if (height < 0){
            return y <= height/width*x + minY+8 - height/width*minX && y >= height/width*x + minY-8 - height/width*(minX)
                    && x >= minX && x <= minX+width && y <= minY && y >= minY+height;
        }
        return y <= height/width*x + minY+8 - height/width*minX && y >= height/width*x + minY-8 - height/width*(minX)
                && x >= minX && x <= minX+width && y >= minY && y <= minY+height;
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
