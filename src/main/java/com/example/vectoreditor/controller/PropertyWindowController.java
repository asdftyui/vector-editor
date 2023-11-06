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
        title.setText(attributes.get("title"));
        width.setText(attributes.get("width"));
        height.setText(attributes.get("height"));
        color.setValue(Color.web(attributes.get("color")));
    }
}
