package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.Subject;

import java.util.Map;

public class CurrentPropertyDisplay implements Observer {
    private PropertyWindowController propertyWindowController;

    public CurrentPropertyDisplay(Subject object, PropertyWindowController propertyWindowController){
        object.registerObserver(this);
        this.propertyWindowController = propertyWindowController;
    }

    @Override
    public void update(Map<String, String> attributes) {
        propertyWindowController.setPropertyWindow(attributes);
    }
}
