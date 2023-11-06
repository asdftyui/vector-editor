package com.example.vectoreditor.model;

import java.util.Map;

public interface Observer {
    public void update(Map<String, String> attributes);
}
