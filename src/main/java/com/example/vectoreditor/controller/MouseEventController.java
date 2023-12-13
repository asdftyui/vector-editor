package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.BasicFunction;
import com.example.vectoreditor.model.Line;
import com.example.vectoreditor.model.ObjectHandler;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MouseEventController {
	
    private MouseEventState state;
    protected Parent root;
    protected ObjectHandler objectHandler;
    protected boolean multiSelectMode = false;
    protected Rectangle selectRectangle;
    protected BasicFunction element;
    protected double minX, minY, height, width;
    protected double startX, startY;
    protected static final int MARGIN = 8;
    protected static final double MIN_W = 30;
    protected static final double MIN_H = 20;
    
    protected void setState(MouseEventState newState) {
        this.state = newState;
    }

    public MouseEventController(Parent root, ObjectHandler objectHandler){
        this.root = root;
        this.objectHandler = objectHandler;
        createSelectRectangle();
        setState(new DefaultState(this));
    }
    

    protected void createSelectRectangle(){
        selectRectangle = new Rectangle();
        selectRectangle.setFill(Color.LIGHTBLUE);
        selectRectangle.setOpacity(0.5);
        selectRectangle.setStroke(Color.BLUE);
        selectRectangle.setStrokeWidth(2);
        ((BorderPane)root).getChildren().add(selectRectangle);
        selectRectangle.setVisible(false);
    }

    protected void setSelectRectangle(){
        selectRectangle.setX(startX);
        selectRectangle.setY(startY);
        selectRectangle.setWidth(0);
        selectRectangle.setHeight(0);
        selectRectangle.setVisible(true);
        selectRectangle.toFront();
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

    protected void onMousePressed(MouseEvent event){
    	state.onMousePressed(event, this);
    }
    
    private void onMouseDragged(MouseEvent event){
        state.onMouseDragged(event, this);
     }   
    
    private void onMouseReleased(MouseEvent event){
    	state.onMouseReleased(event, this);
    }
    
    public void onMouseMoved(MouseEvent event) {
        state.onMouseMoved(event, this);
    }

    protected void setProperties() {
        minX = Double.parseDouble(element.getAttribute().get("x"));
        minY = Double.parseDouble(element.getAttribute().get("y"));
        height = Double.parseDouble(element.getAttribute().get("height"));
        width = Double.parseDouble(element.getAttribute().get("width"));
    }

    protected void updateSelectRectangle(double minX, double minY, double width, double height){
        selectRectangle.setX(minX);
        selectRectangle.setY(minY);
        selectRectangle.setWidth(width);
        selectRectangle.setHeight(height);
    }

    protected void currentMouseState(MouseEvent event) {
    	setState(new DefaultState (this));
    	
        if (isMoveZone(event)){
        	setState(new MoveState (this));
        }

        boolean left = isLeftResizeZone(event);
        boolean right = isRightResizeZone(event);
        boolean top = isTopResizeZone(event);
        boolean bottom = isBottomResizeZone(event);

        if (left && top) setState(new ResizeState(this, ResizeState.S.NW_RESIZE));
        else if (left && bottom) setState(new ResizeState(this, ResizeState.S.SW_RESIZE));
        else if (right && top) setState(new ResizeState(this, ResizeState.S.NE_RESIZE));
        else if (right && bottom) setState(new ResizeState(this, ResizeState.S.SE_RESIZE));
        else if (right) setState(new ResizeState(this, ResizeState.S.E_RESIZE));
        else if (left) setState(new ResizeState(this, ResizeState.S.W_RESIZE));
        else if (top) setState(new ResizeState(this, ResizeState.S.N_RESIZE));
        else if (bottom) setState(new ResizeState(this, ResizeState.S.S_RESIZE));
    }

    protected void currentMouseStateLine(MouseEvent event) {
    	setState(new DefaultState (this));
    	
        if (isMoveZoneLine(event)){
        	setState(new MoveState (this));
        }

        boolean start = isStartZone(event);
        boolean end = isEndZone(event);

        if (start){
        	setState(new ResizeState(this, ResizeState.S.N_RESIZE));
        } else if (end){
        	setState(new ResizeState(this, ResizeState.S.S_RESIZE));
        }
    }
    
    private boolean isMoveZoneLine(MouseEvent event){
        double x = event.getSceneX();
        double y = event.getSceneY();

        if (y <= height/width*x + minY+MARGIN - height/width*(minX) && y >= height/width*x + minY-MARGIN - height/width*(minX)
        && x >= minX+MARGIN && x <= minX+width-MARGIN && y >= minY+MARGIN && y <= minY+height-MARGIN){
            return true;
        } else{
            return false;
        }
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
        return intersect(minX, event.getSceneX()) && event.getSceneY() >= minY && event.getSceneY() <= minY + height;
    }

    private boolean isRightResizeZone(MouseEvent event) {
        return intersect(minX+width, event.getSceneX()) && event.getSceneY() >= minY && event.getSceneY() <= minY + height;
    }

    private boolean isTopResizeZone(MouseEvent event) {
        return intersect(minY, event.getSceneY()) && event.getSceneX() >= minX && event.getSceneX() <= minX + width;
    }

    private boolean isBottomResizeZone(MouseEvent event) {
        return intersect(minY+height, event.getSceneY()) && event.getSceneX() >= minX && event.getSceneX() <= minX + width;
    }

    private boolean intersect(double side, double point) {
        return side + MARGIN > point && side - MARGIN < point;
    }
    
    protected boolean isStartZone(MouseEvent event){
        Circle circle = new Circle(minX, minY, MARGIN);
        return circle.contains(event.getSceneX(), event.getSceneY());
    }

    protected boolean isEndZone(MouseEvent event){
        Circle circle = new Circle(minX+width, minY+height, MARGIN);
        return circle.contains(event.getSceneX(), event.getSceneY());
    }
}

