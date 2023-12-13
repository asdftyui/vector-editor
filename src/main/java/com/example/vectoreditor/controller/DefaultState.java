package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.Line;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class DefaultState implements MouseEventState {
	private MouseEventController econt;

    public DefaultState(MouseEventController econt) {
        this.econt = econt;
    }
    
    @Override
    public void onMousePressed(MouseEvent event, MouseEventController controller) {
    	econt.startX = event.getSceneX();
    	econt.startY = event.getSceneY();
    	if (econt.multiSelectMode && econt.objectHandler.getNumOfSelectedElements() > 1 && econt.objectHandler.isSelectObject(econt.startX, econt.startY)) {
    		econt.setState(new MoveState(econt));
    	} else {
    		if (econt.element instanceof Line) {
    			econt.currentMouseStateLine(event);
    		} else {
    			econt.currentMouseState(event);
    		}
    		econt.objectHandler.selectObject(event.getSceneX(), event.getSceneY());
        	econt.element = econt.objectHandler.getSelectedObject();
        	
        	if (econt.element == null) {
                if (econt.multiSelectMode) {
                	econt.setState(new SelectState(econt));
                	econt.setSelectRectangle();
                }
            } else {
            	econt.setState(new MoveState(econt));
                econt.setProperties();
            }
    	}  	
    }
    
    @Override
    public void onMouseDragged(MouseEvent event, MouseEventController controller) {
    	//
    }
    
    @Override
    public void onMouseMoved(MouseEvent event, MouseEventController controller) {
    	if (econt.element != null && econt.objectHandler.getNumOfSelectedElements() < 2){
            if (econt.element instanceof Line){
                econt.currentMouseStateLine(event);
                Cursor cursor = Cursor.DEFAULT;
                econt.root.setCursor(cursor);
            } else {
            	econt.currentMouseState(event);
                Cursor cursor = Cursor.DEFAULT;
                econt.root.setCursor(cursor);
            }
        }
    }
    
    @Override
    public void onMouseReleased(MouseEvent event, MouseEventController econt) {
    	if (econt.multiSelectMode){
    		econt.selectRectangle.setVisible(false);
        } else if (econt.element != null){
        	econt.root.setCursor(Cursor.DEFAULT);
        }
    }
    
    
}
