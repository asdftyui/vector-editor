package com.example.vectoreditor.model;

import com.example.vectoreditor.controller.Observer;

public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}
