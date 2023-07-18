package com.example.compsciia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Document {

    public static void uploadFile(File sourceFile, String destination) throws IOException {
        //creates a new file with its path being the destination
        File destFile = new File(destination);
        //copies the original file to the destination file
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println(sourceFile.getName() + " uploaded to " + destFile.getName());
    }

    //adds text at the end of a file
    public static void addContent(File file, String content) {
        FileUtilities.writeToFile(file.getPath(), content + "\n", true);
        System.out.println(file.getName() + " updated");
    }

    //overwrites a file's contents
    public static void overWriteContent(File file, String content) {
        FileUtilities.writeToFile(file.getPath(), content + "\n", false);
        System.out.println(file.getName() + " overwritten");
    }

    //checks if file was deleted
    public static void deleteFile(File file, ArrayList<String> directory) {
        file.delete();
        for (int i=0; i<directory.size(); i++){
            if (directory.get(i).equals(file.getName())){
                directory.remove(i);
            }
        }
    }

    public static void createNewFile(String path) throws IOException {
        File newFile = new File(path);
        //checks if file was created
        boolean success = newFile.createNewFile();
        if (success) {
            System.out.println(newFile.getName() + " created successfully");
        } else {
            //if not successful, the file already exists
            System.out.println(newFile.getName() + " already exists");
        }
    }

    public static void moveFile(File documentFilePath, String folderName) throws IOException {
        //stores a new file path based on a folder name
        String filePath = folderName + "/" + documentFilePath.getName();

        //moves the file to the new path
        documentFilePath.renameTo(new File(filePath));
        System.out.println(documentFilePath.getName() + " moved to " + folderName);
    }

    public static void renameFile(File documentFilePath, String stringFilePath, String newName){
        //stores a new file path based on file path and a new name
        String filePath = stringFilePath + "\\" + newName;

        //replaces existing file path with updated name
        documentFilePath.renameTo(new File(filePath));
        System.out.println(documentFilePath.getName() + " renamed to " + newName);
    }

    public static void displayDocument(File document) {
        //stores file contents in an arraylist
        ArrayList<String> content = FileUtilities.returnFileContents(document.getPath());
        String text = "";
        for (String line : content) {
            //updates the variable text for each string in arraylist
            text = text + line + "\n";
        }
        //shows the text
        System.out.println(text);
    }
}