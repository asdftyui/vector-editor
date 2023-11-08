package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.Line;
import com.example.vectoreditor.model.ObjectHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PropertyWindowEventController {
    private ObjectHandler objectHandler;
    private TextField title, width, height;
    private Button front, back, del;
    private ColorPicker color;

    public PropertyWindowEventController(ObjectHandler objectHandler, TextField title, TextField width, TextField height, Button front, Button back, ColorPicker color, Button del){
        this.objectHandler = objectHandler;
        this.title = title;
        this.width = width;
        this.height = height;
        this.front = front;
        this.back = back;
        this.del = del;
        this.color = color;
    }

    public void setEventHandler(){
        title.setOnKeyReleased(this::titleOnKeyReleased);
        width.setOnKeyReleased(this::resizeOnKeyReleased);
        height.setOnKeyReleased(this::resizeOnKeyReleased);
        front.setOnMouseClicked(this::frontOnMouseClicked);
        back.setOnMouseClicked(this::backOnMouseClicked);
        del.setOnMouseClicked(this::delOnMouseClicked);
        color.setOnAction(this::colorOnMouseClicked);
    }

    private void titleOnKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            String newTitle = title.getText();
            objectHandler.setTitle(newTitle);
        }
    }

    private void resizeOnKeyReleased(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER && objectHandler.getNumOfSelectedElements() == 1 && !(objectHandler.getSelectedObject() instanceof Line)){
            String newWidth = width.getText();
            String newHeight = height.getText();
            objectHandler.resizeObject(0, 0, Double.parseDouble(newHeight), Double.parseDouble(newWidth));
        }
    }

    private void frontOnMouseClicked(MouseEvent event){
        objectHandler.changeZOrder(true);
    }

    private void backOnMouseClicked(MouseEvent event){
        objectHandler.changeZOrder(false);
    }

    private void delOnMouseClicked(MouseEvent event){
        objectHandler.deleteObject();
    }

    private void colorOnMouseClicked(ActionEvent event){
        Color newColor = color.getValue();
        objectHandler.setColor(newColor);
    }
}
