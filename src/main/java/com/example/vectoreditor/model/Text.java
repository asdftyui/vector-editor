package com.example.vectoreditor.model;

import com.example.vectoreditor.controller.Observer;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Text extends javafx.scene.text.Text implements BasicFunction, Subject {

    private ArrayList<Observer> observers;
    private String title;
    private String content;

    public Text(int title_Num) {
    	super("New Text"); // 기본 텍스트 설정
        this.content = "New Text"; // 초기 내용 설정
        this.title = "layer " + title_Num;
        this.observers = new ArrayList<>();
        setX(350); // 기본 시작 위치 X
        setY(350); // 기본 시작 위치 Y
        setFont(Font.font("Arial", 20)); // 기본 폰트 설정
        setFill(Color.BLACK); // 기본 색상 설정
        setBoundsType(TextBoundsType.VISUAL);
    }


    public String getTitle() {
        return title;
    }
    
    public String getContent() {
        return content;
    }

    // 내용을 설정하는 메서드입니다.
    public void setContent(String content) {
    	this.content = content;
        setText(content); // JavaFX 텍스트 객체의 내용도 업데이트합니다.
        notifyObservers(); 
    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    @Override
    public Map<String, String> getAttribute() {
        Color color = (Color) getFill();
        String colorString = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        
        double width = getLayoutBounds().getWidth();
        double height = getLayoutBounds().getHeight();

        Map<String, String> attribute = new HashMap<>();
        attribute.put("title", getTitle());
        attribute.put("x", Double.toString(getX()));
        attribute.put("y", Double.toString(getY()));
        attribute.put("width", String.format("%.2f", width));
        attribute.put("height", String.format("%.2f", height));
        attribute.put("textv", getContent()); 
        attribute.put("fontkind", getFont().getFamily());
        attribute.put("fontsize", String.valueOf(getFont().getSize()));
        attribute.put("color", colorString);
        

        return attribute;
    }

    @Override
    public void setColor(Color color) {
        setFill(color);
        notifyObservers();
    }

    
    public void setFontFamily(String fontFamily) {
        setFont(Font.font(fontFamily, getFont().getSize()));
        notifyObservers();
    }

    public void setFontSize(double size) {
        setFont(Font.font(getFont().getFamily(), size));
        notifyObservers();
    }
    

    @Override
    public void moveObject(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
        notifyObservers();
    }

    @Override
    public void resizeObject(double deltaX, double deltaY, double newHeight, double newWidth) {
        //텍스트 리사이즈 불가. 폰트 크기 조정으로만 조절되게 함.
    }
    
    @Override
    public void setZOrder(boolean front) {
        if (front){
            toFront();
        } else {
            toBack();
        }
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
        Shape intersection = Shape.intersect(this, rectangle);

        if (intersection.getBoundsInLocal().getWidth() != -1 || intersection.getBoundsInLocal().getHeight() != -1) {
            // 교차된 영역이 비어있지 않으면 true를 반환
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
        Platform.runLater(() -> {
            for (Observer o : observers) {
                o.update(getAttribute());
            }
        });
    }
}
