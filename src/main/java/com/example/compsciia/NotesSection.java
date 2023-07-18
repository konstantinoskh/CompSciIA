package com.example.compsciia;

import java.io.File;
import java.util.ArrayList;

public class NotesSection {

    public static void createNotesSection(File directory){
        File file = new File(directory,"notes_section.txt");
        FileUtilities.writeToFile(file.getPath(),"",false);
    }

    public static void addNotes(String notes, boolean isChecked){
        FileUtilities.writeToFile("notes_section.txt",notes +  "\n",true);
    }

    public static void deleteNotes(){
        FileUtilities.writeToFile("notes_section.txt", "", false);
    }

    public static void overWriteNotes(String notes, File directory){
        File toDoList = new File(directory, "notes_section.txt");
        FileUtilities.writeToFile(toDoList.getPath(), notes + "\n", false);
    }
    public static String seeCurrentNotes(File directory){
        File notesSection = new File(directory, "notes_section.txt");
        if (!notesSection.exists()){
            NotesSection.createNotesSection(directory);
        }
        ArrayList<String> notes = FileUtilities.returnFileContents(notesSection.getPath());
        String text = "";
        for (String element:notes){
            text = text + element + "\n";
        }
        return text;
    }
}
