package com.example.vectoreditor.model;

import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class ObjectHandler {
    private ObjectFactory objectFactory = new ObjectFactory();
    private ArrayList<BasicFunction> elements = new ArrayList<>();
    private ArrayList<Integer> selectedIndex = new ArrayList<>();

    private BorderPane root;

    public ObjectHandler(BorderPane root) {
        this.root = root;
    }

    public void createObject(String className) {
        if (className.equals("Rectangle")){
            Rectangle rectangle = objectFactory.createRectangle(elements.size()+1);
            elements.add(rectangle);
            root.getChildren().add(rectangle);
        } else if(className.equals("Ellipse")){
            Ellipse ellipse = objectFactory.createEllipse(elements.size()+1);
            elements.add(ellipse);
            root.getChildren().add(ellipse);
        }
    }

    public void changeZOrder(boolean front) {
        if (front) {
            for(Integer index: selectedIndex){
                BasicFunction removedElement = elements.get(index);
                elements.remove(index);
                elements.add(0, removedElement);
            }

        } else {
            for (Integer index: selectedIndex){
                BasicFunction removedElement = elements.get(index);
                elements.remove(index);
                elements.add(removedElement);
            }
        }
    }

    public void deleteObject() {
        elements.remove(selectedIndex);
    }

    public void moveObject(double x, double y) {
        for(Integer index: selectedIndex){
            elements.get(index).moveObject(x, y);
        }
    }

    public void resizeObject(double x, double y, double height, double width) {
        for (Integer index: selectedIndex){
            elements.get(index).resizeObject(x, y, height, width);
        }

    }

    public void selectObject(double pointX, double pointY) {
        selectedIndex.clear();
        for(int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isSelectObject(pointX, pointY)){
                selectedIndex.add(i);
                break;
            }
        }
    }

    public void multiSelectObject(javafx.scene.shape.Rectangle rectangle) {
        selectedIndex.clear();
        for(int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isSelectObject(rectangle)){
                selectedIndex.add(i);
            }
        }
    }

    public int getNumOfSelectedElements(){
        return selectedIndex.size();
    }

    public boolean isSelectObject(double x, double y) {
        for(int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isSelectObject(x, y) && selectedIndex.contains(i)){
                return true;
            }
        }
        return false;
    }

    public BasicFunction getSelectedObject(){
        if (selectedIndex.size() > 0){
            return elements.get(selectedIndex.get(0));
        } else {
            return null;
        }
    }


}
