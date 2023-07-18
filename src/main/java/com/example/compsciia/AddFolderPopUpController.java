package com.example.compsciia;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class AddFolderPopUpController {
    @FXML
    private TextField folderNameField;
    @FXML
    private Button confirmButton;

    public void createFolder() {
        Platform.runLater(() -> {
            MainMenuController controller = ((MainMenuController) folderNameField.getScene().getWindow().getUserData());
            String folderName = folderNameField.getText();
            controller.addFolder(folderName);

            File newFolder = Folder.currentFilePath(folderName, MainMenuController.currentDirectory);
            newFolder.mkdir();
        });
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
