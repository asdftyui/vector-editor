package com.example.vectoreditor.model;

import com.example.vectoreditor.controller.Observer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageM extends ImageView implements BasicFunction, Subject {
    private ArrayList<Observer> observers;
    private String title;
    private double originalWidth;
    private double originalHeight;

    public ImageM(String imagePath, int title_num, double canvasWidth, double canvasHeight) {
        super();
        this.title = "layer" + title_num;
        this.observers = new ArrayList<>();

        try {
            Image image = new Image(new FileInputStream(imagePath), canvasWidth, canvasHeight, true, true);
            if (!image.isError()) {
                this.setImage(image);
                this.originalWidth = image.getWidth();
                this.originalHeight = image.getHeight();
                this.setPreserveRatio(true);
                
                this.setFitWidth(originalWidth);
                this.setFitHeight(originalHeight);
            } else {
                // Handle image loading error
                System.out.println("Error loading image.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Handle the case where the image file could not be loaded
        }
    }

    
    public void centerImageOnCanvas(double canvasWidth, double canvasHeight) {
        // 이미지의 실제 너비와 높이를 가져옵니다.
        double imageWidth = this.getBoundsInParent().getWidth();
        double imageHeight = this.getBoundsInParent().getHeight();

        // 이미지를 캔버스 중앙에 배치하기 위한 x, y 좌표를 계산합니다.
        double x = (canvasWidth - imageWidth) / 2;
        double y = (canvasHeight - imageHeight) / 2;

        // 이미지의 위치를 조정합니다.
        this.setX(x);
        this.setY(y);
    }
    
    public String getTitle() {
        return title;
    }

    @Override
    public Map<String, String> getAttribute() {
        // 현재 화면상의 크기를 가져오기 위해 getBoundsInParent() 사용
        double width = this.getBoundsInParent().getWidth();
        double height = this.getBoundsInParent().getHeight();

        Map<String, String> attribute = new HashMap<>();
        attribute.put("title", getTitle());
        attribute.put("x", Double.toString(getX()));
        attribute.put("y", Double.toString(getY()));
        attribute.put("height", Double.toString(height));
        attribute.put("width", Double.toString(width));

        return attribute;
    }

    
    @Override
    public void setColor(Color color) {
        // 이미지에 대해서는 색상 변경을 지원하지 않으므로 이 메소드는 비워둡니다.
    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }


    @Override
    public void setZOrder(boolean front) {
        if (front) {
            toFront();
        } else{
            toBack();
        }
    }

    @Override
    public void moveObject(double x, double y) {
        // Update the position of the ImageView
        double newX = getX() + x;
        double newY = getY() + y;
        setX(newX);
        setY(newY);
        notifyObservers();
    }

    @Override
    public void resizeObject(double deltaX, double deltaY, double newHeight, double newWidth) {
        // PreserveRatio를 false로 설정하여 비율 유지를 비활성화합니다.
        setPreserveRatio(false);

        // 사용자가 지정한 새로운 너비와 높이를 설정합니다.
        setFitWidth(newWidth);
        setFitHeight(newHeight);

        // 이미지의 위치를 조정합니다.
        if (deltaX != 0 || deltaY != 0) {
            setX(getX() + deltaX);
            setY(getY() + deltaY);
        }

        // 옵저버에게 변경 사항을 알립니다.
        notifyObservers();
    }

    
    @Override
    public boolean isSelectObject(double pointX, double pointY) {
        if (contains(pointX, pointY)){
            notifyObservers();
            return true;
        }
        return false;
    }

    @Override
    public boolean isSelectObject(javafx.scene.shape.Rectangle rectangle) {
        // ImageView의 경계를 가져옵니다.
        Bounds imageViewBounds = this.getBoundsInParent();

        // Rectangle의 경계를 가져옵니다.
        Bounds selectionBounds = rectangle.getBoundsInParent();

        // 두 경계가 겹치는지 확인합니다.
        if (imageViewBounds.intersects(selectionBounds)) {
            notifyObservers();
            return true;
        }
        return false;
    }


    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        // Ensure that updates are run on the JavaFX Application Thread
        Platform.runLater(() -> {
            for (Observer o : observers) {
                o.update(getAttribute());
            }
        });
    }
}
