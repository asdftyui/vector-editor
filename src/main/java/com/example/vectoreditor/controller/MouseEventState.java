package com.example.vectoreditor.controller;

import javafx.scene.input.MouseEvent;

public interface MouseEventState {
    void onMousePressed(MouseEvent event, MouseEventController controller);
    void onMouseDragged(MouseEvent event, MouseEventController controller);
    void onMouseMoved(MouseEvent event, MouseEventController controller);
    void onMouseReleased(MouseEvent event, MouseEventController controller);
}
