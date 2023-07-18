package com.example.compsciia;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label accountDoesntExistLabel;
    @FXML
    private Label passwordIsIncorrectLabel;
    @FXML
    private Label enterUsernameLabel;
    public static String username;

    @FXML
    public void initialize(){
        usernameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                try{
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    handleEnterKey(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                try{
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    handleEnterKey(stage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



    public void loginButton(ActionEvent e) throws Exception {
        Platform.runLater(() -> {
            try {
                File file = new File("users.txt");

                if (!file.exists()) {
                    FileUtilities.writeToFile("users.txt", "", true);
                }

                FileDecryption.decryptFile();

                username = usernameTextField.getText();
                String password = passwordField.getText();

                if (username.length() == 0) {
                    accountDoesntExistLabel.setVisible(false);
                    passwordIsIncorrectLabel.setVisible(false);
                    enterUsernameLabel.setVisible(true);
                    FileEncryption.encryptFile();
                } else if (!Database.checkUsername("users.txt", username)) {
                    accountDoesntExistLabel.setVisible(true);
                    passwordIsIncorrectLabel.setVisible(false);
                    enterUsernameLabel.setVisible(false);
                    FileEncryption.encryptFile();
                } else if (!Database.checkUsernameAndPassword("users.txt", username, password)) {
                    accountDoesntExistLabel.setVisible(false);
                    passwordIsIncorrectLabel.setVisible(true);
                    enterUsernameLabel.setVisible(false);
                    FileEncryption.encryptFile();
                } else {
                    username = usernameTextField.getText();
                    FileEncryption.encryptFile();
                    Folder userFolder = new Folder(username);
                    if (!userFolder.folderExists()) {
                        userFolder.createFolder();
                    }

                    File[] files = userFolder.getFile().listFiles();

                    assert files != null;
                    if (files.length == 0) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("subjectChoice.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            stage.setTitle("Subject Choice");
                            Image icon = new Image("C:\\Users\\khkon\\IdeaProjects\\HomeworkWizzz\\src\\main\\resources\\Images\\HomeworkWizz.jpg");
                            stage.getIcons().add(icon);
                            stage.setResizable(false);
                            stage.setScene(new Scene(root, 900, 600));
                            stage.show();
                        } catch (IOException exception) {
                            System.out.println("IOException");
                        } catch (RuntimeException r) {
                            r.printStackTrace();
                        } catch (Exception exception) {
                            System.out.println("Exception");
                        }
                    } else {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            stage.setTitle("Main Menu");
                            Image icon = new Image("C:\\Users\\khkon\\IdeaProjects\\HomeworkWizzz\\src\\main\\resources\\Images\\HomeworkWizz.jpg");
                            stage.getIcons().add(icon);
                            stage.setResizable(false);
                            stage.setScene(new Scene(root, 900, 600));
                            stage.show();
                        } catch (IOException exception) {
                            System.out.println("IOException");
                        } catch (RuntimeException r) {
                            System.out.println("Runtime Exception");
                        } catch (Exception exception) {
                            System.out.println("Exception");
                        }
                    }
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
    }

    private void handleEnterKey(Stage stage) {
        Platform.runLater(() -> {
            try {
                File file = new File("users.txt");

                if (!file.exists()) {
                    FileUtilities.writeToFile("users.txt", "", true);
                }

                FileDecryption.decryptFile();

                username = usernameTextField.getText();
                String password = passwordField.getText();

                if (username.length() == 0) {
                    accountDoesntExistLabel.setVisible(false);
                    passwordIsIncorrectLabel.setVisible(false);
                    enterUsernameLabel.setVisible(true);
                    FileEncryption.encryptFile();
                } else if (!Database.checkUsername("users.txt", username)) {
                    accountDoesntExistLabel.setVisible(true);
                    passwordIsIncorrectLabel.setVisible(false);
                    enterUsernameLabel.setVisible(false);
                    FileEncryption.encryptFile();
                } else if (!Database.checkUsernameAndPassword("users.txt", username, password)) {
                    accountDoesntExistLabel.setVisible(false);
                    passwordIsIncorrectLabel.setVisible(true);
                    enterUsernameLabel.setVisible(false);
                    FileEncryption.encryptFile();
                } else {
                    LoginController.username = usernameTextField.getText();
                    FileEncryption.encryptFile();
                    Folder userFolder = new Folder(username);
                    if (!userFolder.folderExists()) {
                        userFolder.createFolder();
                    }

                    File[] files = userFolder.getFile().listFiles();

                    assert files != null;
                    if (files.length == 0) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("subjectChoice.fxml"));
                            Parent root = fxmlLoader.load();
                            stage.setTitle("Subject Choice Menu");
                            Image icon = new Image("C:\\Users\\khkon\\IdeaProjects\\HomeworkWizzz\\src\\main\\resources\\Images\\HomeworkWizz.jpg");
                            stage.getIcons().add(icon);
                            stage.setScene(new Scene(root, 900, 600));
                            stage.setResizable(true);
                            stage.show();
                        } catch (IOException exception) {
                            System.out.println("IOException");
                        } catch (RuntimeException r) {
                            r.printStackTrace();
                        } catch (Exception exception) {
                            System.out.println("Exception");
                        }
                    } else {
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
                            exception.printStackTrace();
                        } catch (RuntimeException r) {
                            System.out.println("Runtime Exception");
                        } catch (Exception exception) {
                            System.out.println("Exception");
                        }
                    }
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
    }

    public void signupButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Sign Up");
            Image icon = new Image("C:\\Users\\khkon\\IdeaProjects\\HomeworkWizzz\\src\\main\\resources\\Images\\HomeworkWizz.jpg");
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.setScene(new Scene(root, 520, 400));
            stage.show();
        }catch (IOException e){
            System.out.println("Error loading screen: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}