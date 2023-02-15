package com.example.quan_li_ks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class hamMain extends Application {

    double x=0;
    double y=0;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = null;
        FXMLLoader loader = new FXMLLoader(DBultil.class.getResource("home.fxml"));
        root= loader.load();
        stage.setTitle("Hello!");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch();
    }
}