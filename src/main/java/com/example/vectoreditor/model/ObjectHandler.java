package com.example.vectoreditor.model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class ObjectHandler {
    private ArrayList<GraphicObject> elements = new ArrayList<>();
    private int selectedIndex;

    public void createObject(String className) throws Exception {
        Class<?> elementClass = Class.forName(className);

        // 클래스의 생성자 정보 얻기
        Constructor<?> constructor = elementClass.getConstructor(String.class, double.class, double.class, Object[].class);

        // 객체 생성
        elements.add((GraphicObject) constructor.newInstance());
    }

    public void changeZOrder(boolean front) {
        if (front) {
            elements.add(0, elements.remove(selectedIndex));
        } else {
            elements.add(elements.remove(selectedIndex));
        }
    }

    public void deleteObject() {
        elements.remove(selectedIndex);
    }

    public void moveObject(double x, double y) {
        elements.get(selectedIndex).move(x, y);
    }

    public void resizeObject(double height, double width) {
        elements.get(selectedIndex).resize(height, width);
    }

    public void rotateObject(double angle) {
        elements.get(selectedIndex).rotate(angle);
    }

    public void selectObject(double pointX, double pointY) {
        for(int i = elements.size()-1; i >= 0; i--) {
            double x = elements.get(i).getX();
            double y = elements.get(i).getY();
            double height = elements.get(i).getHeight();
            double width = elements.get(i).getWidth();

            if (pointX >= x && pointX <= x+width && pointY >= y && pointY <= y+height){
                selectedIndex = i;
                break;
            }
        }
    }

    public void multiSelectObject() {

    }
}
