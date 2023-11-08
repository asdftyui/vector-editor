package com.example.vectoreditor.controller;

import java.util.Map;

public interface Observer {
    public void update(Map<String, String> attributes);
}
