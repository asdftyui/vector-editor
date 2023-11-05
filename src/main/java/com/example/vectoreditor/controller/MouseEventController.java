package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.BasicFunction;
import com.example.vectoreditor.model.ObjectHandler;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MouseEventController {
    public static enum S {
        DEFAULT,
        SELECT,
        MOVE,
        NW_RESIZE,
        SW_RESIZE,
        NE_RESIZE,
        SE_RESIZE,
        E_RESIZE,
        W_RESIZE,
        N_RESIZE,
        S_RESIZE;
    }
    private S state = S.DEFAULT;
    private Parent root;
    private ObjectHandler objectHandler;
    private boolean multiSelectMode = false;
    private Rectangle selectRectangle;
    private BasicFunction element;
    private double minX, minY, height, width;
    private double startX, startY;
    private static final int MARGIN = 8;
    private static final double MIN_W = 30;
    private static final double MIN_H = 20;

    public MouseEventController(Parent root, ObjectHandler objectHandler){
        this.root = root;
        this.objectHandler = objectHandler;
        createSelectRectangle();
    }

    private void createSelectRectangle(){
        selectRectangle = new Rectangle();
        selectRectangle.setFill(Color.LIGHTBLUE);
        selectRectangle.setOpacity(0.5);
        selectRectangle.setStroke(Color.BLUE);
        selectRectangle.setStrokeWidth(2);
        ((BorderPane)root).getChildren().add(selectRectangle);
        selectRectangle.setVisible(false);
    }

    private void setSelectRectangle(){
        selectRectangle.setX(startX);
        selectRectangle.setY(startY);
        selectRectangle.setWidth(0);
        selectRectangle.setHeight(0);
        selectRectangle.setVisible(true);
    }

    public void setMouseEvent(boolean mode){
        multiSelectMode = mode;
        root.setOnMousePressed(null);
        root.setOnMouseDragged(null);
        root.setOnMouseReleased(null);
        root.setOnMouseMoved(this::onMouseMoved);
        root.setOnMousePressed(this::onMousePressed);
        root.setOnMouseDragged(this::onMouseDragged);
        root.setOnMouseReleased(this::onMouseReleased);
    }

    private void onMousePressed(MouseEvent event){
        startX = event.getSceneX();
        startY = event.getSceneY();
        if (multiSelectMode) {
            if (objectHandler.isSelectObject(startX, startY)){
                state = S.MOVE;

            } else{
                objectHandler.selectObject(event.getSceneX(), event.getSceneY());

                if (objectHandler.isEmptySelectedElements()){
                    state = S.SELECT;
                    setSelectRectangle();
                } else {
                    state = S.MOVE;
                }
            }
        } else{
            state = currentMouseState(event);
            if (state.equals(S.DEFAULT) || state.equals(S.MOVE)){
                objectHandler.selectObject(event.getSceneX(), event.getSceneY());
                element = objectHandler.getSelectedObject();
            }

            if (element == null) {
                state = S.DEFAULT;
            } else {
                if (state.equals(S.DEFAULT)) {
                    state = S.MOVE;
                }
                setProperties();
            }
        }
    }

    private void setProperties() {
        minX = Double.parseDouble(element.getAttribute().get("x"));
        minY = Double.parseDouble(element.getAttribute().get("y"));
        height = Double.parseDouble(element.getAttribute().get("height"));
        width = Double.parseDouble(element.getAttribute().get("width"));
    }

    private void onMouseDragged(MouseEvent event){
        if (multiSelectMode) {
            if (state.equals(S.SELECT)) {
                double endX = event.getSceneX();
                double endY = event.getSceneY();

                double minX = Math.min(startX, endX);
                double minY = Math.min(startY, endY);
                double width = Math.abs(endX - startX);
                double height = Math.abs(endY - startY);

                updateSelectRectangle(minX, minY, width, height);
                objectHandler.multiSelectObject(selectRectangle);
            } else{
                objectHandler.moveObject(event.getSceneX() - startX, event.getSceneY() - startY);
                startX = event.getSceneX();
                startY = event.getSceneY();
            }
        }
        if (state != S.DEFAULT && state != S.SELECT) {
            if (state == S.MOVE) {
                objectHandler.moveObject(event.getSceneX() - startX, event.getSceneY() - startY);
                startX = event.getSceneX();
                startY = event.getSceneY();
                setProperties();
            } else {
                double newW = width;
                double newH = height;
                double deltaX = 0;
                double deltaY = 0;

                // Right Resize
                if (state == S.E_RESIZE || state == S.NE_RESIZE || state == S.SE_RESIZE) {
                    newW = event.getSceneX() - minX;
                }
                // Left Resize
                if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE) {
                    deltaX = event.getSceneX() - minX;
                    newW = width + minX - event.getSceneX();
                }

                // Bottom Resize
                if (state == S.S_RESIZE || state == S.SE_RESIZE || state == S.SW_RESIZE) {
                    newH = event.getSceneY() - minY;
                }
                // Top Resize
                if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE) {
                    deltaY = event.getSceneY() - minY;
                    newH = height + minY - event.getSceneY();
                }

                //min valid rect Size Check
                if (newW < MIN_W) {
                    if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE)
                        deltaX = width - MIN_W;
                    newW = MIN_W;
                }

                if (newH < MIN_H) {
                    if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE)
                        deltaY = height - MIN_H;
                    newH = MIN_H;
                }
                objectHandler.resizeObject(deltaX, deltaY, newH, newW);
                setProperties();
            }
        }
    }

    private void updateSelectRectangle(double minX, double minY, double width, double height){
        selectRectangle.setX(minX);
        selectRectangle.setY(minY);
        selectRectangle.setWidth(width);
        selectRectangle.setHeight(height);
    }

    private void onMouseReleased(MouseEvent event){
        if (multiSelectMode){
            selectRectangle.setVisible(false);
        } else if (element != null){
            root.setCursor(Cursor.DEFAULT);
        }
    }

    public void onMouseMoved(MouseEvent event) {
        if (!multiSelectMode && element != null){
            S state = currentMouseState(event);
            Cursor cursor = getCursorForState(state);
            root.setCursor(cursor);
        }
    }

    private static Cursor getCursorForState(S state) {
        switch (state) {
            case NW_RESIZE:
                return Cursor.NW_RESIZE;
            case SW_RESIZE:
                return Cursor.SW_RESIZE;
            case NE_RESIZE:
                return Cursor.NE_RESIZE;
            case SE_RESIZE:
                return Cursor.SE_RESIZE;
            case E_RESIZE:
                return Cursor.E_RESIZE;
            case W_RESIZE:
                return Cursor.W_RESIZE;
            case N_RESIZE:
                return Cursor.N_RESIZE;
            case S_RESIZE:
                return Cursor.S_RESIZE;
            default:
                return Cursor.DEFAULT;
        }
    }

    private S currentMouseState(MouseEvent event) {
        S state = S.DEFAULT;

        if (isMoveZone(event)){return S.MOVE;}

        boolean left = isLeftResizeZone(event);
        boolean right = isRightResizeZone(event);
        boolean top = isTopResizeZone(event);
        boolean bottom = isBottomResizeZone(event);

        if (left && top) state = S.NW_RESIZE;
        else if (left && bottom) state = S.SW_RESIZE;
        else if (right && top) state = S.NE_RESIZE;
        else if (right && bottom) state = S.SE_RESIZE;
        else if (right) state = S.E_RESIZE;
        else if (left) state = S.W_RESIZE;
        else if (top) state = S.N_RESIZE;
        else if (bottom) state = S.S_RESIZE;

        return state;
    }

    private boolean isMoveZone(MouseEvent event) {
        double xPos = event.getSceneX();
        double yPos = event.getSceneY();
        double nodeX = minX + MARGIN;
        double nodeY = minY + MARGIN;
        double nodeX0 = minX + width - MARGIN;
        double nodeY0 = minY + height - MARGIN;

        return (xPos > nodeX && xPos < nodeX0) && (yPos > nodeY && yPos < nodeY0);
    }

    private boolean isLeftResizeZone(MouseEvent event) {
        return intersect(minX, event.getSceneX());
    }

    private boolean isRightResizeZone(MouseEvent event) {
        return intersect(minX+width, event.getSceneX());
    }

    private boolean isTopResizeZone(MouseEvent event) {
        return intersect(minY, event.getSceneY());
    }

    private boolean isBottomResizeZone(MouseEvent event) {
        return intersect(minY+height, event.getSceneY());
    }

    private boolean intersect(double side, double point) {
        return side + MARGIN > point && side - MARGIN < point;
    }
}


