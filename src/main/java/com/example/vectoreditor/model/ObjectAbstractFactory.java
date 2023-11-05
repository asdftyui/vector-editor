package com.example.vectoreditor.model;

public interface ObjectAbstractFactory {
    Rectangle createRectangle(int title_num);
    Ellipse createEllipse(int title_num);
}
