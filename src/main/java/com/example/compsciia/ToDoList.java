package com.example.compsciia;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ToDoList {

    //returns the string contents of the to do list
    private String returnToDoList(ArrayList<ToDoListObject> list) {
        String stringToDoList = "";
        for (ToDoListObject element : list) {
            stringToDoList = stringToDoList + element.getContents() + ": " +  element.isFinished()+ "\n";
        }
        return stringToDoList;
    }


    public static void addTasks(ArrayList<ToDoListObject> tasks, File directory) {
        File toDoList = new File(directory, "to_do_list.txt");
        StringBuilder text = new StringBuilder();

        for (ToDoListObject task : tasks) {
            String taskName = task.getContents();
            boolean isDone = task.isFinished();

            String checkBox = taskName + ":" + isDone + "\n";
            text.append(checkBox);
        }
        FileUtilities.writeToFile(toDoList.getPath(), String.valueOf(text), false);
    }

    //reads the contents of the to do list file and shows it to the user
    public static void seeExistingToDoList(){
        ArrayList<String> contents = FileUtilities.returnFileContents("to_do_list.txt");
        String text = "";
        for (String line: contents){
            text = text + line + "\n";
        }
        System.out.println(text);
    }

    //changes the boolean value of a to-do list object
    public void tickItemOff(String item){
        //creates a random access file
        try (RandomAccessFile raf = new RandomAccessFile("to_do_list.txt", "rw")){
            //reads the whole file's contents
            while (raf.getFilePointer() < raf.length()){
                String line = raf.readLine();
                //splits the line into two based on the regex ":"
                String firstElement = line.split(":")[0].strip();
                String secondElement = line.split(":")[1].strip();

                //if the passed item exists in the to-do list, check its boolean value
                if (firstElement.equals(item)){
                    if (secondElement.equals("true")){
                        System.out.println("Item is already ticked off");
                        return;
                    }
                    //set the file pointer back right before where "false" is
                    long filePointer = raf.getFilePointer() - 6;
                    raf.seek(filePointer);
                    //replace false with true
                    raf.write("true ".getBytes());
                    //set the file pointer to right after true
                    filePointer = raf.getFilePointer() - 1;
                    raf.seek(filePointer);
                    break;
                }
                System.out.println("Item not found");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void createToDoList(File directory){
        File toDoList = new File(directory, "to_do_list.txt");
        FileUtilities.writeToFile(toDoList.getPath(),"",false);
    }

    public static ArrayList<String> returnToDoListItems(File directory){
        File toDoList = new File(directory, "to_do_list.txt");
        ArrayList<String> content = FileUtilities.returnFileContents(toDoList.getPath());
        ArrayList<String> items = new ArrayList<>();

        for(String line : content){
            String taskName = line.split(":")[0];
            String isChecked = line.split(":")[1];
            boolean isTrue = Boolean.parseBoolean(isChecked);

            String item = taskName + ":" + isTrue;
            items.add(item);
        }
        return items;
    }

    //change a to-do list object's string content
    /*public void rewriteItem(String text, String newText){
        for (ToDoListObject element: this.toDoList){
            if(element.getContents().strip().equalsIgnoreCase(text)){
                element.setContents(newText);
                FileUtilities.writeToFile("to_do_list.txt", returnToDoList(this.toDoList), false);
                return;
            }
        }
        System.out.println("Element doesn't exist");
    }

    //same as the create to-do list method but it appends an item rather than overwrite the list
    /*public void addToDoItems(String task) {
        ToDoListObject toDoListObject = new ToDoListObject(task);
        this.toDoList.addAll(toDoListObject); // add only the new items to the current list
        setToDoList(this.toDoList);
        FileUtilities.writeToFile("to_do_list.txt", returnToDoList(this.toDoList), true);
    }*/
}