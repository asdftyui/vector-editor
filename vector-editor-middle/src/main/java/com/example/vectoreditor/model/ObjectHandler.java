package com.example.vectoreditor.model;

import com.example.vectoreditor.controller.CurrentPropertyDisplay;
import com.example.vectoreditor.controller.PropertyWindowController;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight; 


import java.util.ArrayList;

public class ObjectHandler {
    private PropertyWindowController propertyWindowController;
    private ArrayList<BasicFunction> elements = new ArrayList<>();
    private ArrayList<BasicFunction> selectedIndex = new ArrayList<>();
    private BorderPane root;

    public ObjectHandler(BorderPane root, PropertyWindowController propertyWindowController) {
        this.root = root;
        this.propertyWindowController = propertyWindowController;
    }
    
    public void createObject(String className) {
        createObject(className, null);
    }
    
    public void createObject(String className, String imagePath) {
        if (className.equals("Rectangle")){
            Rectangle rectangle = new Rectangle(elements.size()+1);
            elements.add(0, rectangle);
            root.getChildren().add(rectangle);
            new CurrentPropertyDisplay(rectangle, propertyWindowController);
        } else if(className.equals("Ellipse")){
            Ellipse ellipse = new Ellipse(elements.size()+1);
            elements.add(0, ellipse);
            root.getChildren().add(ellipse);
            new CurrentPropertyDisplay(ellipse, propertyWindowController);
        } else if(className.equals("Line")){
            Line line = new Line(elements.size()+1);
            elements.add(0, line);
            root.getChildren().add(line);
            new CurrentPropertyDisplay(line, propertyWindowController);
        } else if(className.equals("ImageM")) {
        	if (imagePath != null && !imagePath.isEmpty()) {
        		double canvasWidth = root.getWidth(); // 캔버스의 너비를 가져옵니다.
                double canvasHeight = root.getHeight();
                ImageM imageM = new ImageM(imagePath, elements.size() + 1, canvasWidth*0.4, canvasHeight*0.4);
                imageM.centerImageOnCanvas(canvasWidth, canvasHeight);
                elements.add(0, imageM);
                root.getChildren().add(imageM);
                new CurrentPropertyDisplay(imageM, propertyWindowController);
            } else {
                System.out.println("Image path is not provided for ImageM object creation.");
            }
        } else if(className.equals("Text")) {
            Text text = new Text(elements.size() + 1);
            elements.add(0, text); // elements 리스트에 추가
            root.getChildren().add(text); // UI에 텍스트 추가
            new CurrentPropertyDisplay(text, propertyWindowController);
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
        for (BasicFunction element: selectedIndex){
            element.setColor(color);
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
    
    public void setTextContent(String content) {
        if (getNumOfSelectedElements() == 1 && getSelectedObject() instanceof Text) {
            Text selectedText = (Text) getSelectedObject();
            selectedText.setContent(content);
        }
    }

    public void setFontSize(double size) {
        for (BasicFunction element : selectedIndex) {
            if (element instanceof Text) {
                ((Text) element).setFontSize(size);
            }
        }
    }

    // Method to set the font family of the selected text object
    public void setFontFamily(String family) {
        for (BasicFunction element : selectedIndex) {
            if (element instanceof Text) {
                ((Text) element).setFontFamily(family);
            }
        }
    }
}
