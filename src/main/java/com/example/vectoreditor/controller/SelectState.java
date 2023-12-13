package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.Line;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class SelectState implements MouseEventState {
    private MouseEventController econt;

    public SelectState(MouseEventController econt) {
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
        	
        	if (econt.element == null) {
                if (econt.multiSelectMode) {
                	econt.setSelectRectangle();
                } else {
                	econt.setState(new DefaultState(econt));
                }
            } else {
                econt.setProperties();
            }
    	}  	      
    }

    @Override
    public void onMouseDragged(MouseEvent event, MouseEventController controller) {
    	if(econt.multiSelectMode) {
    		double endX = event.getSceneX();
    	    double endY = event.getSceneY();
	
	        double minX = Math.min(econt.startX, endX);
	        double minY = Math.min(econt.startY, endY);
	        double width = Math.abs(endX - econt.startX);
	        double height = Math.abs(endY - econt.startY);
	
	        econt.updateSelectRectangle(minX, minY, width, height);
	        econt.objectHandler.multiSelectObject(econt.selectRectangle);
	        econt.element = econt.objectHandler.getSelectedObject();
    	}
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
