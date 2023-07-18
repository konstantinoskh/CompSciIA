package com.example.compsciia;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.MenuItem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainMenuController {
    @FXML
    private Label labelOne;
    @FXML
    private Label labelTwo;
    @FXML
    private Label labelThree;
    @FXML
    private Label labelFour;
    @FXML
    private Label labelFive;
    @FXML
    private Label labelSix;
    @FXML
    private Label welcomeMessage;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> searchResults;
    @FXML
    private Label noFileFoundLabel;
    @FXML
    private Label noUserInputLabel;
    @FXML
    private TextArea textBox;
    @FXML
    private ListView<CheckBox> todolistListView;
    @FXML
    private ListView<String> fileListView;
    @FXML
    private Button closeButton;
    @FXML
    private Button fileListViewBackButton;
    @FXML
    private ContextMenu fileListViewContextMenu;
    @FXML
    private MenuItem renameOption;
    ObservableList<String> items;
    public static Account account = new Account(LoginController.username);
    public static ArrayList<String> currentDirectory = new ArrayList<>();
    private ArrayList<String> subjects;


    @FXML
    public void initialize() throws IOException {
        subjects = new ArrayList<>();
        account = new Account(LoginController.username);
        File file = new File(account.getUsername());
        File[] files = file.listFiles();
        assert files != null;
        for (File existingFile : files) {
            if (existingFile.isDirectory()) {
                subjects.add(existingFile.getName());
            }
        }

        String username = account.getUsername();
        welcomeMessage.setText("Welcome, " + username);
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(labelOne, labelTwo, labelThree, labelFour, labelFive, labelSix));
        for (int i = 0; i < subjects.size(); i++) {
            Label label = labels.get(i);
            String subject = subjects.get(i);
            label.setText(subject);
        }

        Platform.runLater(() -> {
            Scene scene = welcomeMessage.getScene();
            scene.setOnMouseClicked(event -> {
                if (!searchResults.getBoundsInParent().contains(event.getX(), event.getY())) {
                    searchResults.setVisible(false);
                    noFileFoundLabel.setVisible(false);
                    noUserInputLabel.setVisible(false);
                }
            });
            scene.getWindow().setOnCloseRequest(windowEvent -> {
                try {
                    saveText();
                    saveToDoList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Platform.runLater(() -> {
                labelOne.setOnMouseClicked(event -> onSubjectCLick(labelOne));
                labelTwo.setOnMouseClicked(event -> onSubjectCLick(labelTwo));
                labelThree.setOnMouseClicked(event -> onSubjectCLick(labelThree));
                labelFour.setOnMouseClicked(event -> onSubjectCLick(labelFour));
                labelFive.setOnMouseClicked(event -> onSubjectCLick(labelFive));
                labelSix.setOnMouseClicked(event -> onSubjectCLick(labelSix));
            });
        });

        File directory = new File(LoginController.username);
        File notes = new File(directory, "notes_section.txt");
        File toDoList = new File(directory, "to_do_list.txt");

        if (!notes.exists()) {
            NotesSection.createNotesSection(directory);
        } else {
            String text = NotesSection.seeCurrentNotes(directory);
            textBox.setText(text);
        }

        if (toDoList.createNewFile()) {
            ToDoList.createToDoList(directory);
        } else {
            ArrayList<String> tasks = ToDoList.returnToDoListItems(directory);

            for (int i = 0; i < tasks.size(); i++) {
                String task = tasks.get(i);
                String taskName = task.split(":")[0];
                String isFinished = task.split(":")[1];
                boolean isDone = Boolean.parseBoolean(isFinished);
                CheckBox checkBox = new CheckBox(taskName);
                todolistListView.getItems().add(checkBox);

                if (isDone) {
                    todolistListView.getItems().get(i).fire();
                }
            }
        }

        fileListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (fileListView.getSelectionModel().isEmpty()) {
                    fileListView.setContextMenu(null);
                } else {
                    fileListView.setContextMenu(fileListViewContextMenu);
                    fileListView.requestFocus();
                }
            }
        });
        fileListView.setOnMousePressed(this::onFileClick);

        fileListView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (fileListView.getSelectionModel().isEmpty()) {
                    // Show the context menu at the mouse position
                    fileListViewContextMenu.show(fileListView, event.getScreenX(), event.getScreenY());
                }
            }
        });
        items = fileListView.getItems();
        fileListView.setItems(items);
    }

    public void deleteFile() {
        String selectedItem = fileListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null){
            return;
        }
        File selectedFile = Folder.currentFilePath(selectedItem, currentDirectory);
        fileListView.getItems().removeIf(item -> item.equals(selectedItem));
        Document.deleteFile(selectedFile, currentDirectory);
    }

    @FXML
    public void searchConfirmButton() {
        noUserInputLabel.setVisible(false);
        noFileFoundLabel.setVisible(false);
        String userInput = searchBar.getText();
        searchResults.getItems().clear();
        searchResults.setVisible(false);
        fileListView.setVisible(false);
        closeButton.setVisible(false);
        fileListViewBackButton.setVisible(false);

        if (userInput.length() != 0) {
            File directory = new File(account.getUsername());

            ArrayList<File> results = Search.find(directory, userInput);
            if (results.size() == 0) {
                noFileFoundLabel.setVisible(true);
            } else {
                for (File file : results) {
                    searchResults.getItems().add(file.getName());
                    searchResults.setVisible(true);
                }
                searchResults.setUserData(results);
            }
        } else {
            noUserInputLabel.setVisible(true);
        }
    }

    public void saveText() {
        File directory = new File(LoginController.username);
        String content = textBox.getText();
        NotesSection.overWriteNotes(content, directory);
    }

    public void saveToDoList() throws IOException {
        ObservableList<CheckBox> toDoList = todolistListView.getItems();
        ArrayList<ToDoListObject> toDoListObjects = new ArrayList<>();

        File directory = new File(LoginController.username);
        File toDoListFile = new File(directory, "to_do_list.txt");

        if (!toDoListFile.exists()) {
            ToDoList.createToDoList(directory);
        }

        for (CheckBox task : toDoList) {
            String taskName = task.getText();
            boolean isChecked = task.isSelected();
            ToDoListObject newItem = new ToDoListObject(taskName, isChecked);
            toDoListObjects.add(newItem);

            ToDoList.addTasks(toDoListObjects, directory);
        }
    }

    @FXML
    public void addButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addButtonPopUp.fxml"));
        VBox root = loader.load();

        // Create a new stage for the pop-up window
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Prevent interactions with other windows
        popupStage.initOwner(textBox.getScene().getWindow()); // Set the main window as the owner
        popupStage.setScene(new Scene(root));
        popupStage.setUserData(this);
        popupStage.setResizable(false);
        popupStage.showAndWait();
    }

    public void addTask(String taskName) {
        CheckBox task = new CheckBox(taskName);
        todolistListView.getItems().add(task);
    }
    public void addFolder(String folderName) {
        fileListView.getItems().add(folderName);
    }

    private void onSubjectCLick(Label clickedLabel) {
        fileListView.getItems().clear();
        searchResults.setVisible(false);
        File directory = new File(LoginController.username);
        String fileName = clickedLabel.getText();
        File file = new File(directory, fileName);
        ArrayList<File> files = Search.listFiles(file);

        assert files != null;
        for (File file1 : files) {
            fileListView.getItems().add(file1.getName());
            fileListView.setVisible(true);
            closeButton.setVisible(true);
            fileListViewBackButton.setVisible(true);

            if (currentDirectory.size() != 0) {
                currentDirectory.clear();
            }
        }
    currentDirectory.add(LoginController.username);
    currentDirectory.add(fileName);
    }


    private void onFolderCLick(String fileName) {
        fileListView.getItems().clear();
        String filePath = "";

        for (int i = 0; i <= currentDirectory.size() - 2; i++) {
            filePath = filePath + currentDirectory.get(i) + "\\";
        }

        filePath = filePath + currentDirectory.get(currentDirectory.size() - 1);

        File file = new File(filePath, fileName);
        ArrayList<File> files = Search.listFiles(file);

        assert files != null;
        for (File file1 : files) {
            fileListView.getItems().add(file1.getName());
            fileListView.setVisible(true);
            closeButton.setVisible(true);
            fileListViewBackButton.setVisible(true);
        }
        currentDirectory.add(fileName);
    }

    public void closeButton() {
        fileListView.setVisible(false);
        closeButton.setVisible(false);
        fileListViewBackButton.setVisible(false);
    }

    private void onFileClick() {
        Platform.runLater(() -> {
            String selectedItem = fileListView.getSelectionModel().getSelectedItem();
            searchResults.setVisible(false);
            if (selectedItem != null) {
                String filePath = "";

                for (int i = 0; i <= currentDirectory.size() - 2; i++) {
                    filePath = filePath + currentDirectory.get(i) + "\\";
                }
                filePath = filePath + currentDirectory.get(currentDirectory.size() - 1);

                File directory = new File(filePath);
                File file = new File(directory, selectedItem);

                if (file.isDirectory()) {
                    onFolderCLick(selectedItem);
                } else {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.open(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }


    public void onSearchItemClick() {
        int selectedIndex = searchResults.getSelectionModel().getSelectedIndex();
        ArrayList<File> files = (ArrayList<File>) searchResults.getUserData();


        File file = files.get(selectedIndex);
        if (file.isDirectory()) {
            onSearchFileCLick(file.getPath());
        } else {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onSearchFileCLick(String filePath) {
        fileListView.getItems().clear();

        File file = new File(filePath);
        ArrayList<File> files = Search.listFiles(file);

        if (files.size() == 0) {
            fileListView.setVisible(true);
            closeButton.setVisible(true);
            fileListViewBackButton.setVisible(true);
        } else {
            for (File file1 : files) {
                fileListView.getItems().add(file1.getName());
                fileListView.setVisible(true);
                closeButton.setVisible(true);
                fileListViewBackButton.setVisible(true);
            }
            currentDirectory.clear();
            currentDirectory = new ArrayList<>(Arrays.asList(filePath.split("\\\\")));
            searchResults.setVisible(false);
        }
    }

    public void backButton() {
        fileListView.getItems().clear();
        String filePath = "";

        if (currentDirectory.size() == 2) {
            for (int i = 0; i <= currentDirectory.size() - 2; i++) {
                filePath = filePath + currentDirectory.get(i) + "\\";
            }
            filePath = filePath + currentDirectory.get(currentDirectory.size() - 1);

            File folder = new File(filePath);
            File[] files = folder.listFiles();

            assert files != null;
            for (File file : files) {
                String filename = file.getName();
                fileListView.getItems().add(filename);
            }
            return;
        }

        try {
            currentDirectory.remove(currentDirectory.size() - 1);
            for (int i = 0; i <= currentDirectory.size() - 2; i++) {
                filePath = filePath + currentDirectory.get(i) + "\\";
            }
            filePath = filePath + currentDirectory.get(currentDirectory.size() - 1);
        } catch (IndexOutOfBoundsException exception) {
            File directory = new File(LoginController.username);
            File[] files = directory.listFiles();
            for (File file : files) {
                fileListView.getItems().add(file.getName());
            }
            return;
        }

        File file = new File(filePath);
        ArrayList<File> files = Search.listFiles(file);

        assert files != null;
        if (files.size() == 0) {
            fileListView.setVisible(true);
            closeButton.setVisible(true);
            fileListViewBackButton.setVisible(true);
        } else {
            for (File file1 : files) {
                fileListView.getItems().add(file1.getName());
                fileListView.setVisible(true);
                closeButton.setVisible(true);
                fileListViewBackButton.setVisible(true);
            }
        }
    }

    public void toDoListClear() {
        todolistListView.getItems().removeIf(CheckBox::isSelected);
    }

    private void onFileClick(MouseEvent event) {
         if (event.getClickCount() == 2) {
            onFileClick();
        }
    }
    public void newFolder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFolderPopUp.fxml"));
        VBox root = loader.load();

        // Create a new stage for the pop-up window
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Prevent interactions with other windows
        popupStage.initOwner(textBox.getScene().getWindow()); // Set the main window as the owner
        popupStage.setScene(new Scene(root));
        popupStage.setUserData(this);
        popupStage.setResizable(false);
        popupStage.showAndWait();
    }

    @FXML
    public void renameContextMenuItem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("renameFilePopUp.fxml"));
        VBox root = loader.load();

        // Create a new stage for the pop-up window
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Prevent interactions with other windows
        popupStage.initOwner(textBox.getScene().getWindow()); // Set the main window as the owner
        popupStage.setScene(new Scene(root));
        popupStage.setUserData(this);
        popupStage.setResizable(false);
        popupStage.showAndWait();
    }

    public File returnCurrentFile(){
        String currentSelection = fileListView.getSelectionModel().getSelectedItem();
        File currentFile = Folder.currentFilePath(currentSelection, currentDirectory);
        return currentFile;
    }

    public void refreshFileListView(String newName){
        int index = fileListView.getSelectionModel().getSelectedIndex();
        items.set(index, newName);
    }
}