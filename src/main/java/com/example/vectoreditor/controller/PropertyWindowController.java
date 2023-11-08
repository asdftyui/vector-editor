package com.example.vectoreditor.controller;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Map;

public class PropertyWindowController {
    private TextField title, width, height;
    private ColorPicker color;

    public PropertyWindowController(TextField title, TextField width, TextField height, ColorPicker color){
        this.title = title;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void setPropertyWindow(Map<String, String> attributes){
        double widthValue= Double.parseDouble(attributes.get("width"));
        double heightValue = Double.parseDouble(attributes.get("height"));

        title.setText(attributes.get("title"));
        width.setText(Double.toString(Math.round(Math.abs(widthValue))));
        height.setText(Double.toString(Math.round(Math.abs(heightValue))));
        color.setValue(Color.web(attributes.get("color")));
    }
}
