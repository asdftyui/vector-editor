package com.example.vectoreditor.controller;

import com.example.vectoreditor.model.Line;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;


public class ResizeState implements MouseEventState {
	public static enum S {
        NW_RESIZE,
        SW_RESIZE,
        NE_RESIZE,
        SE_RESIZE,
        E_RESIZE,
        W_RESIZE,
        N_RESIZE,
        S_RESIZE;
    }
	private S state;
	private MouseEventController econt;

    public ResizeState(MouseEventController econt, S state) {
        this.econt = econt;
        this.state = state;
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
                	econt.setState(new SelectState(econt));
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
    	double newW = econt.width;
        double newH = econt.height;
        double deltaX = 0;
        double deltaY = 0;
        
        if (econt.element instanceof Line){
            if (state == S.N_RESIZE){
                deltaX = Math.min(event.getSceneX(), econt.minX+econt.width);
                deltaY = event.getSceneY();
                newW = 0;
            } else if (state == S.S_RESIZE) {
                deltaX = Math.max(event.getSceneX(), econt.minX);
                deltaY = event.getSceneY();
                newW = -1;
            }
        } else {
            // Right Resize
            if (state == S.E_RESIZE || state == S.NE_RESIZE || state == S.SE_RESIZE) {
                newW = event.getSceneX() - econt.minX;
            }
            // Left Resize
            if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE) {
                deltaX = event.getSceneX() - econt.minX;
                newW = econt.width + econt.minX - event.getSceneX();
            }

            // Bottom Resize
            if (state == S.S_RESIZE || state == S.SE_RESIZE || state == S.SW_RESIZE) {
                newH = event.getSceneY() - econt.minY;
            }
            // Top Resize
            if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE) {
                deltaY = event.getSceneY() - econt.minY;
                newH = econt.height + econt.minY - event.getSceneY();
            }

            if (newW < econt.MIN_W) {
                if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE)
                    deltaX = econt.width - econt.MIN_W;
                newW = econt.MIN_W;
            }

            if (newH < econt.MIN_H) {
                if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE)
                    deltaY = econt.height - econt.MIN_H;
                newH = econt.MIN_H;
            }
        }
        econt.objectHandler.resizeObject(deltaX, deltaY, newH, newW);
        econt.setProperties();
    }

    @Override
    public void onMouseMoved(MouseEvent event, MouseEventController controller) {
    	if (econt.element != null && econt.objectHandler.getNumOfSelectedElements() < 2){
            if (econt.element instanceof Line){
                econt.currentMouseStateLine(event);
                Cursor cursor = getCursorForState(state);
                econt.root.setCursor(cursor);
            } else {
                econt.currentMouseState(event);
                Cursor cursor = getCursorForState(state);
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
     
}