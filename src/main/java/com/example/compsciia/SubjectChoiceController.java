package com.example.compsciia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class SubjectChoiceController {
    @FXML
    private TextField subjectOneField;
    @FXML
    private TextField subjectTwoField;
    @FXML
    private TextField subjectThreeField;
    @FXML
    private TextField subjectFourField;
    @FXML
    private TextField subjectFiveField;
    @FXML
    private TextField subjectSixField;
    @FXML
    private Button confirmButton;
    @FXML
    private Label enterAllSubjectsMessage;
    @FXML
    private Button subjectChoiceBackButton;
    public static Account account = new Account(LoginController.username);

    @FXML
    public void initialize(){
        subjectOneField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                try{
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    confirmKey(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        subjectTwoField.setOnKeyPressed( event -> {
            if (event.getCode() == KeyCode.ENTER){
                try{
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    confirmKey(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        subjectThreeField.setOnKeyPressed( event -> {
            if (event.getCode() == KeyCode.ENTER){
                try{
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    confirmKey(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        subjectFourField.setOnKeyPressed( event -> {
            if (event.getCode() == KeyCode.ENTER){
                try{
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    confirmKey(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        subjectFiveField.setOnKeyPressed( event -> {
            if (event.getCode() == KeyCode.ENTER){
                try{
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    confirmKey(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        subjectSixField.setOnKeyPressed( event -> {
            if (event.getCode() == KeyCode.ENTER){
                try{
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    confirmKey(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void subjectChoiceBackButton(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Log In");
            Image icon = new Image("C:\\Users\\khkon\\IdeaProjects\\HomeworkWizzz\\src\\main\\resources\\Images\\HomeworkWizz.jpg");
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.setScene(new Scene(root, 520, 400));
            stage.show();
        }catch (IOException e){
            System.out.println("Couldn't load screen: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Runtime Exception");
        }catch (Exception e){
            System.out.println("Exception");
        }
    }

    public void confirmButton(ActionEvent e){
        boolean completed = true;
        String subjectOne = subjectOneField.getText();
        String subjectTwo = subjectTwoField.getText();
        String subjectThree = subjectThreeField.getText();
        String subjectFour = subjectFourField.getText();
        String subjectFive = subjectFiveField.getText();
        String subjectSix = subjectSixField.getText();

        ArrayList<String> subjects = new ArrayList<>(Arrays.asList(subjectOne, subjectTwo, subjectThree, subjectFour, subjectFive,subjectSix));

        for(String subject : subjects){
            if (subject.length() == 0){
                enterAllSubjectsMessage.setVisible(true);
                completed = false;
            }
        }

        if (completed){
            account.setSubjects(subjects);
            enterAllSubjectsMessage.setVisible(false);
            for (String subject : subjects){
                File folder = new File(LoginController.username, subject);
                folder.mkdir();
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setTitle("Main Menu");
                Image icon = new Image("C:\\Users\\khkon\\IdeaProjects\\HomeworkWizzz\\src\\main\\resources\\Images\\HomeworkWizz.jpg");
                stage.getIcons().add(icon);
                stage.setResizable(true);
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void confirmKey(Stage stage){
        boolean completed = true;
        String subjectOne = subjectOneField.getText();
        String subjectTwo = subjectTwoField.getText();
        String subjectThree = subjectThreeField.getText();
        String subjectFour = subjectFourField.getText();
        String subjectFive = subjectFiveField.getText();
        String subjectSix = subjectSixField.getText();

        ArrayList<String> subjects = new ArrayList<>(Arrays.asList(subjectOne, subjectTwo, subjectThree, subjectFour, subjectFive,subjectSix));

        for(String subject : subjects){
            if (subject.length() == 0){
                enterAllSubjectsMessage.setVisible(true);
                completed = false;
            }
        }

        if (completed){
            account.setSubjects(subjects);
            enterAllSubjectsMessage.setVisible(false);
            for (String subject : subjects){
                File folder = new File(LoginController.username, subject);
                folder.mkdir();
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
                Parent root = fxmlLoader.load();
                stage.setTitle("Main Menu");
                Image icon = new Image("C:\\Users\\khkon\\IdeaProjects\\HomeworkWizzz\\src\\main\\resources\\Images\\HomeworkWizz.jpg");
                stage.getIcons().add(icon);
                stage.setResizable(true);
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
            } catch (IOException exception) {
                System.out.println("IOException");
            } catch (RuntimeException r) {
                r.printStackTrace();
            } catch (Exception exception) {
                System.out.println("Exception");
            }
        }
    }
}
