package com.example.vectoreditor.controller;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Map;

public class PropertyWindowController {
    private TextField title, width, height, textv;
    private ColorPicker color;
    private Slider fontsize;
    private ComboBox<String> fontkind;

    public PropertyWindowController(TextField title, TextField width, TextField height, ColorPicker color, TextField textv, Slider fontsize, ComboBox<String> fontkind){
        this.title = title;
        this.width = width;
        this.height = height;
        this.color = color;
        this.textv = textv;
        this.fontkind = fontkind;
        this.fontsize = fontsize;
    }

    public void setPropertyWindow(Map<String, String> attributes){
        double widthValue= Double.parseDouble(attributes.get("width"));
        double heightValue = Double.parseDouble(attributes.get("height"));

        title.setText(attributes.get("title"));
        width.setText(Double.toString(Math.round(Math.abs(widthValue))));
        height.setText(Double.toString(Math.round(Math.abs(heightValue))));
        
        if (attributes.containsKey("color")) {
            color.setDisable(false); // 색상 선택기를 활성화
            color.setValue(Color.web(attributes.get("color")));
        } else {
            // 색상 정보가 없는 경우 색상 선택기를 비활성화합니다.
            color.setDisable(true); // 색상 선택기를 비활성화
            color.setValue(Color.TRANSPARENT); // 선택된 색상을 투명으로 설정
        }
        
        if (attributes.containsKey("textv") && textv != null) {
            textv.setDisable(false);
            width.setDisable(true);
            height.setDisable(true);
            textv.setText(attributes.get("textv"));
        } else if (textv != null) {
            textv.setDisable(true);
            width.setDisable(false);
            height.setDisable(false);
            textv.setText("");
        }

        
        if (attributes.containsKey("fontkind")) {
            fontkind.setDisable(false);
            String fontName = attributes.get("fontkind");
            fontkind.getSelectionModel().select(fontName);
        } else {
            fontkind.setDisable(true);
            fontkind.getSelectionModel().clearSelection();
        }
        
        if (attributes.containsKey("fontsize")) {
            fontsize.setDisable(false);
            double fontSizeValue = Double.parseDouble(attributes.get("fontsize"));
            fontsize.setValue(fontSizeValue);
        } else {
            fontsize.setDisable(true);
            fontsize.setValue(Font.getDefault().getSize());
        }
    }
}
