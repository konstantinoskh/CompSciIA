package com.example.compsciia;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RenameFilePopUpController {
    @FXML
    private TextField newFileNameField;
    @FXML
    private Button confirmButton;

    public void renameFile() {
        Platform.runLater(() -> {
            MainMenuController controller = ((MainMenuController) newFileNameField.getScene().getWindow().getUserData());
            File currentFile = controller.returnCurrentFile();
            String newFolderName = newFileNameField.getText();
            File newFileNameFile = Folder.currentFilePath(newFolderName, MainMenuController.currentDirectory);
            currentFile.renameTo(newFileNameFile);
            controller.refreshFileListView(newFolderName);

        });
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
