package com.example.compsciia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Folder {
    private String name;
    private File file;


    public Folder(String name){
        this.name = name;
        file = new File(name);
    }

    public boolean createFolder(){
        //returns true if the directory is created successfully
        return file.mkdirs();
    }

    public boolean folderExists(){
       return getFile().exists();
    }

    //override constructor in case a name isn't given
    public Folder(){
        //if name isn't given, the name becomes default
        this.name = "default";
        createFolder();
    }

    public void renameFile(File currentFile, String newName) throws IOException {
        //returns a list of files within the directory
        File[] files = getFile().listFiles();
        if (files == null) {
            System.out.println("Folder doesn't contain any files");
            return;
        }
        for (File file : files){
            //if the name passed as a parameter is the same as an existing filename rename the file
            if (file.isFile() && file.getName().equals(currentFile.getName())){
                Document.renameFile(currentFile, this.file.getName(), newName);
                return;
            }
        }
    }

    File getFile() {
        return file;
    }

    //creates a file in a folder with the name passed as a parameter
    public void newFileInFolder(String fileName) throws IOException {
        String path = this.name + "/" + fileName;
        Document.createNewFile(path);
    }

    //adds text to a specific file in a folder
    public void writeToFile(String text, String name){
        File[] files = getFile().listFiles();
        if (files == null) {
            System.out.println("Folder doesn't contain any files");
            return;
        }
        for (File file : files){
            //if the file exists, add the passed text into the file
            if (file.isFile() && file.getName().equals(name)){
                Document.addContent(file, text);
                break;
            }
        }
    }

    //overwrites a specific file in a folder
    public void overWriteFile(String fileName, String text){
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("Folder doesn't contain any files");
            return;
        }
        for (File file : files){
            //if the file exists, overwrite the file with the passed text
            if (file.isFile() && file.getName().equals(fileName)){
                Document.overWriteContent(file, text);
                return;
            }
        }
    }

    //copies a file into a different folder
    public void uploadFile(File sourceFile, String destination) throws IOException {
        File[] files = getFile().listFiles();
        //changes the destination path so that the file is placed under the destination folder
        destination = destination + sourceFile.getName();
        if (files == null) {
            System.out.println("Folder doesn't contain any files");
            return;
        }
        for (File file : files){
            //if the file exists in the folder, copy it to the destination folder
            if (file.isFile() && file.getName().equals(sourceFile.getName())){
                Document.uploadFile(sourceFile, destination);
                return;
            }
        }
    }

    //moves a file from one folder into another
    public void moveFile(File sourceFile, String destinationFile) throws IOException {
        File[] files = getFile().listFiles();
        if (files == null) {
            System.out.println("Folder doesn't contain any files");
            return;
        }
        for (File file : files){
            //if the passed file exists, moves it
            if (file.isFile() && file.getName().equals(sourceFile.getName())){
                Document.moveFile(sourceFile, destinationFile);
                return;
            }
        }
    }

    //displays existing file in a folder
    public void displayDocument(String fileName){
        File[] files = file.listFiles();
        if (files == null){
            System.out.println("Folder doesn't contain any files");
            return;
        }
        for (File file : files){
            //if passed file exists, displays the file's contents
            if (file.getName().equals(fileName)){
                Document.displayDocument(file);
                break;
            }
        }
    }

    //returns an existing file
    public File returnFile(String fileName){
        File[] files = getFile().listFiles();
        File existingFile = null;
        if (files == null){
            System.out.println("Folder doesn't contain any files");
            return null;
        }
        for (File file : files){
            //if file exists, returns it
            if (file.isFile() && file.getName().equals(fileName)){
                existingFile = file;
            }
        }
        return existingFile;
    }

    //shows the existing files in a folder
    public void displayFilesInFolder(){
        File[] files = getFile().listFiles();
        String names = "";
        if (files == null) {
            System.out.println("Folder doesn't contain any files");
            return;
        }
        //for each file, add the filename to the string names
        for (File file : files ){
            names = names + file.getName() + "\n";
        }
        //displays the filenames
        System.out.println("Files in " + getFile().getName() + ": \n" + names);
    }

    public String getName() {
        return name;
    }

    public static File currentFilePath(String fileName, ArrayList<String> directory) {
        String filePath = "";
        if (directory.size() == 1) {
            filePath = directory.get(0);
        } else {
            for (int i = 0; i <= directory.size() - 2; i++) {
                filePath = filePath + directory.get(i) + "\\";
            }
            filePath = filePath + directory.get(directory.size() - 1);

        }
        return new File(filePath, fileName);
    }
}
