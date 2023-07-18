package com.example.compsciia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HomeworkWizz extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeworkWizz.class.getResource("login.fxml"));
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Log In");
            Image icon = new Image("C:\\Users\\khkon\\IdeaProjects\\HomeworkWizzz\\src\\main\\resources\\Images\\HomeworkWizz.jpg");
            stage.getIcons().add(icon);
            stage.setResizable(false);
            Scene scene = new Scene(fxmlLoader.load(), 520, 400);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }catch (RuntimeException e){
            System.out.println("RuntimeException");
        }
    }
    public static void main(String[] args) {
        launch();
    }
}