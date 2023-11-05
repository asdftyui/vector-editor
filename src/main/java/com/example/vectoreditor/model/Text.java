package com.example.vectoreditor.model;

import javafx.scene.paint.Color;

import java.util.Map;

public class Text extends javafx.scene.text.Text {

    private String title;

    public String getTitle() {
        return title;
    }

    public Map<String, String> getAttribute() {
        Color color = (Color) getFill();

        Map<String, String> attribute = Map.of(
                "title", getTitle(),
                "detail", getText(),
                "font", getFont().getFamily(),
                "size", Double.toString(getFont().getSize()),
                "color", "#" + Integer.toHexString((int) (color.getRed() * 255))
                        + Integer.toHexString((int) (color.getGreen() * 255))
                        + Integer.toHexString((int) (color.getBlue() * 255))
        );
        return attribute;
    }
}
