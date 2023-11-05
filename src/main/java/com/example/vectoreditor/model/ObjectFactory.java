package com.example.vectoreditor.model;

public class ObjectFactory implements ObjectAbstractFactory{
    @Override
    public Rectangle createRectangle(int title_num) {
        return new Rectangle(title_num);
    }

    @Override
    public Ellipse createEllipse(int title_num) {
        return new Ellipse(title_num);
    }
}
