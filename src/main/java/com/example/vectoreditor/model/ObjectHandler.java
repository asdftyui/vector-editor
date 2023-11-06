package com.example.vectoreditor.model;

import com.example.vectoreditor.controller.PropertyWindowController;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ObjectHandler {
    private PropertyWindowController propertyWindowController;
    private ObjectFactory objectFactory = new ObjectFactory();
    private ArrayList<BasicFunction> elements = new ArrayList<>();
    private ArrayList<BasicFunction> selectedIndex = new ArrayList<>();

    private BorderPane root;

    public ObjectHandler(BorderPane root, PropertyWindowController propertyWindowController) {
        this.root = root;
        this.propertyWindowController = propertyWindowController;
    }

    public void createObject(String className) {
        if (className.equals("Rectangle")){
            Rectangle rectangle = objectFactory.createRectangle(elements.size()+1);
            elements.add(0, rectangle);
            root.getChildren().add(rectangle);
            new CurrentPropertyDisplay(rectangle, propertyWindowController);
        } else if(className.equals("Ellipse")){
            Ellipse ellipse = objectFactory.createEllipse(elements.size()+1);
            elements.add(0, ellipse);
            root.getChildren().add(ellipse);
            new CurrentPropertyDisplay(ellipse, propertyWindowController);
        }
    }

    public void changeZOrder(boolean front) {
        if (front) {
            for (int i = selectedIndex.size()-1; i >= 0; i--){
                BasicFunction removedObject= selectedIndex.get(i);
                elements.remove(removedObject);
                elements.add(0, removedObject);
                removedObject.setZOrder(true);
            }

        } else {
            for (BasicFunction element: selectedIndex){
                elements.remove(element);
                elements.add(element);
                element.setZOrder(false);
            }
        }
    }

    public void deleteObject() {
        for (BasicFunction element: selectedIndex){
            elements.remove(element);
            root.getChildren().remove(element);
        }
    }

    public void setTitle(String title) {
        if (getNumOfSelectedElements() == 1){
            selectedIndex.get(0).setTitle(title);
        }
    }

    public void moveObject(double x, double y) {
        for(BasicFunction element: selectedIndex){
            element.moveObject(x, y);
        }
    }

    public void resizeObject(double x, double y, double height, double width) {
        for (BasicFunction element: selectedIndex){
            element.resizeObject(x, y, height, width);
        }
    }

    public void setColor(Color color){
        if (getNumOfSelectedElements() == 1){
            selectedIndex.get(0).setColor(color);
        }
    }

    public void selectObject(double pointX, double pointY) {
        selectedIndex.clear();
        for(int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isSelectObject(pointX, pointY)){
                selectedIndex.add(elements.get(i));
                break;
            }
        }
    }

    public void multiSelectObject(javafx.scene.shape.Rectangle rectangle) {
        selectedIndex.clear();
        for(int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isSelectObject(rectangle)){
                selectedIndex.add(elements.get(i));
            }
        }
    }

    public int getNumOfSelectedElements(){
        return selectedIndex.size();
    }

    public boolean isSelectObject(double x, double y) {
        for(int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isSelectObject(x, y) && selectedIndex.contains(elements.get(i))){
                return true;
            }
        }
        return false;
    }

    public BasicFunction getSelectedObject(){
        if (selectedIndex.size() > 0){
            return selectedIndex.get(0);
        } else {
            return null;
        }
    }
}
