package com.example.vectoreditor.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Map;

public class PropertyWindowController {
    private TextField title, width, height;
    private Button front, back, del;
    private ColorPicker color;

    public PropertyWindowController(TextField title, TextField width, TextField height, Button front, Button back, ColorPicker color, Button del){
        this.title = title;
        this.width = width;
        this.height = height;
        this.front = front;
        this.back = back;
        this.del = del;
        this.color = color;
    }

    public void setPropertyWindow(Map<String, String> attributes){
        title.setText(attributes.get("title"));
        width.setText(attributes.get("width"));
        height.setText(attributes.get("height"));
        color.setValue(Color.web(attributes.get("color")));
    }
}
