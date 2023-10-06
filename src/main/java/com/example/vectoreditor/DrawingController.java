package com.example.vectoreditor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawingController implements Initializable {
    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private double lastX, lastY;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(this::startDrawing);
        canvas.setOnMouseDragged(this::draw);
    }

    private void startDrawing(MouseEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }

    private void draw(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(2);
        graphicsContext.strokeLine(lastX, lastY, x, y);

        lastX = x;
        lastY = y;
    }
}