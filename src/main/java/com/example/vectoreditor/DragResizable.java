package com.example.vectoreditor;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class DragResizable {
    private double nodeX, nodeY, nodeH, nodeW;
    private double clickX, clickY;
    private boolean isSelect = false;
    private Node node;
    private static final int MARGIN = 8;
    private static final double MIN_W = 30;
    private static final double MIN_H = 20;

    public static enum S {
        DEFAULT,
        DRAG,
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

    public DragResizable(Node node) {
        this.node = node;
    }

    public static void makeDragResizable(Node node){
        final DragResizable dragResizable = new DragResizable(node);
        dragResizable.setMouseHandlers();
    }

    private void setMouseHandlers() {
        node.setOnMousePressed(this::onMousePressed);
        node.setOnMouseDragged(this::onMouseDragged);
        node.setOnMouseReleased(this::onMouseReleased);
        node.setOnMouseMoved(this::onMouseMoved);
    }

    private void onMousePressed(MouseEvent event) {
        state = currentMouseState(event);
        setNewInitialEventCoordinates(event);

        if (state == S.DEFAULT){
            setIsSelect(false);
        } else if(state == S.DRAG){
            setIsSelect(true);
        }
    }

    private boolean isDragZone(MouseEvent event) {
        double xPos = event.getSceneX();
        double yPos = event.getSceneY();
        double nodeX = node.getBoundsInParent().getMinX() + MARGIN;
        double nodeY = node.getBoundsInParent().getMinY() + MARGIN;
        double nodeX0 = node.getBoundsInParent().getMaxX() - MARGIN;
        double nodeY0 = node.getBoundsInParent().getMaxY() - MARGIN;

        return (xPos > nodeX && xPos < nodeX0) && (yPos > nodeY && yPos < nodeY0);
    }

    private void setNewInitialEventCoordinates(MouseEvent event) {
        nodeX = node.getBoundsInParent().getMinX();
        nodeY = node.getBoundsInParent().getMinY();
        nodeH = node.getBoundsInParent().getHeight();
        nodeW = node.getBoundsInParent().getWidth();
        clickX = event.getSceneX();
        clickY = event.getSceneY();
    }

    private void onMouseDragged(MouseEvent event) {
        if (state != S.DEFAULT) {
            if (state == S.DRAG) {
                double newX = event.getSceneX() - clickX;
                double newY = event.getSceneY() - clickY;

                if (node instanceof Rectangle) {
                    ((Rectangle) node).setX(nodeX + newX);
                    ((Rectangle) node).setY(nodeY + newY);
                }
            } else {
                double newW = nodeW;
                double newH = nodeH;
                double deltaX = 0;
                double deltaY = 0;

                // Right Resize
                if (state == S.E_RESIZE || state == S.NE_RESIZE || state == S.SE_RESIZE) {
                    newW = event.getSceneX() - nodeX;
                }
                // Left Resize
                if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE) {
                    deltaX = event.getSceneX() - nodeX;
                    newW = nodeW + nodeX - event.getSceneX();
                }

                // Bottom Resize
                if (state == S.S_RESIZE || state == S.SE_RESIZE || state == S.SW_RESIZE) {
                    newH = event.getSceneY() - nodeY;
                }
                // Top Resize
                if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE) {
                    deltaY = event.getSceneY() - nodeY;
                    newH = nodeH + nodeY - event.getSceneY();
                }

                //min valid rect Size Check
                if (newW < MIN_W) {
                    if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE)
                        deltaX = nodeW - MIN_W;
                    newW = MIN_W;
                }

                if (newH < MIN_H) {
                    if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE)
                        deltaY = nodeH - MIN_H;
                    newH = MIN_H;
                }

                System.out.println(deltaX + " " + deltaY + " " + newH + " " + newW);

                if (node instanceof Rectangle) {
                    ((Rectangle) node).setX(nodeX + deltaX);
                    ((Rectangle) node).setY(nodeY + deltaY);
                    ((Rectangle) node).setWidth(newW);
                    ((Rectangle) node).setHeight(newH);
                }
            }
        }
    }

    private void onMouseReleased(MouseEvent event) {
        node.setCursor(Cursor.DEFAULT);
        state = S.DEFAULT;
    }

    private void onMouseMoved(MouseEvent event) {
        S state = currentMouseState(event);
        Cursor cursor = getCursorForState(state);
        node.setCursor(cursor);
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

    public void setIsSelect(boolean select){
        isSelect = select;
        if (select) {
            if (node instanceof Shape){
                ((Shape) node).setStroke(Color.RED);
            }
        } else{
            if (node instanceof Shape){
                ((Shape) node).setStroke(null);
            }
        }
    }

    private S currentMouseState(MouseEvent event) {
        S state = S.DEFAULT;

        if (isDragZone(event)){return S.DRAG;}

        boolean left = isLeftResizeZone(event);
        boolean right = isRightResizeZone(event);
        boolean top = isTopResizeZone(event);
        boolean bottom = isBottomResizeZone(event);

        if ((left || right || top || bottom) && !isSelect) {
            return S.DRAG;
        }

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

    private boolean isLeftResizeZone(MouseEvent event) {
        return intersect(node.getBoundsInParent().getMinX(), event.getSceneX());
    }

    private boolean isRightResizeZone(MouseEvent event) {
        return intersect(node.getBoundsInParent().getMaxX(), event.getSceneX());
    }

    private boolean isTopResizeZone(MouseEvent event) {
        return intersect(node.getBoundsInParent().getMinY(), event.getSceneY());
    }

    private boolean isBottomResizeZone(MouseEvent event) {
        return intersect(node.getBoundsInParent().getMaxY(), event.getSceneY());
    }

    private boolean intersect(double side, double point) {
        return side + MARGIN > point && side - MARGIN < point;
    }
}