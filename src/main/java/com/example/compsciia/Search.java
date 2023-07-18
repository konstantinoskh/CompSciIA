package com.example.compsciia;

import java.io.File;
import java.util.ArrayList;

public class Search {
    public static ArrayList<File> find(File directory, String userInput) {
        File[] files = directory.listFiles();
        ArrayList<File> output = new ArrayList<>();
//        if (files != null) {
//            for (File file : files) {
//                System.out.println(userInput);
//                System.out.println(file.getName());
//                if (userInput.equalsIgnoreCase(file.getName())) {
//                    searchResults.getItems().add(file.getName());
//                    searchResults.setVisible(true);
//                    fileFound = true; // File is found
//                } else if (!userInput.equalsIgnoreCase(file.getName())) {
//                    File[] innerFiles = file.listFiles();
//                    if (innerFiles != null) {
//                        for (File innerFile : innerFiles) {
//                            if (userInput.equalsIgnoreCase(innerFile.getName())) {
//                                searchResults.getItems().add(innerFile.getName());
//                                searchResults.setVisible(true);
//                                fileFound = true; // File is found
//
//                            } else if (!userInput.equalsIgnoreCase(innerFile.getName())) {
//                                if (innerFile.listFiles() == null) {
//                                    noFileFoundLabel.setVisible(true);
//                                    return;
//                                }
//                                File[] subFiles = innerFile.listFiles();
//                                assert subFiles != null;
//                                for (File subFile : subFiles) {
//                                    if (userInput.equalsIgnoreCase(subFile.getName())) {
//                                        searchResults.getItems().add(subFile.getName());
//                                        searchResults.setVisible(true);
//                                        fileFound = true;
//                                    }
//                                }
//                            }
//                        }
//                    }else {
//                        noFileFoundLabel.setVisible(true);
//                    }
//                }
//                if (!fileFound) {
//                    noFileFoundLabel.setVisible(true);
//                }
//            }
//        }
//        else return null;
        if (files != null) {
            for (File file : files) {
                if (fullTextSearch(file.getName(), userInput)) {
                    output.add(file);
                }
                if (file.isDirectory()) {
                    output.addAll(Search.find(file, userInput));
                }
            }
        }
        return output;
    }

    public static ArrayList<File> listFiles (File file) {
        File[] files = file.listFiles();
        ArrayList<File> output = new ArrayList<>();

        if (file.isDirectory()) {
            if (files != null) {
                for (File file1 : files) {
                    output.add(file1);
                }
            }else
                return null;
        }
        return output;
    }

    private static boolean fullTextSearch(String text, String query) {
        // Convert query to lowercase for case-insensitive search
        String lowercaseQuery = query.toLowerCase();

        // Convert word to lowercase for case-insensitive comparison
        String lowercaseWord = text.toLowerCase();

        // Check if the query is present in the word
        if (lowercaseWord.contains(lowercaseQuery)) {
            return true;
        }
        return false;
    }
}
