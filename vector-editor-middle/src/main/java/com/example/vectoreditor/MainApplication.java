package com.example.vectoreditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Vector Editor App");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("app.fxml"));
        Parent root = loader.load();

        double screenWidth = (Screen.getPrimary().getBounds().getWidth())*0.9;
        double screenHeight = (Screen.getPrimary().getBounds().getHeight())*0.9;

        // 1500과 screenWidth 중 큰 값을 선택
        double finalWidth = Math.max(1500, screenWidth);
        // 800과 screenHeight 중 큰 값을 선택
        double finalHeight = Math.max(800, screenHeight);

        Scene scene = new Scene(root, finalWidth, finalHeight);
        primaryStage.setScene(scene);

        primaryStage.show();

        Canvas canvas = (Canvas) ((BorderPane) root).getCenter();
        Rectangle clip = new Rectangle(canvas.getWidth(), canvas.getHeight());
        canvas.setClip(clip);
        System.out.println(root.localToScene(canvas.getBoundsInLocal()).getMinX());
    }
}