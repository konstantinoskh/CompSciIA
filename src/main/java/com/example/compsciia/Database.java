package com.example.compsciia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Database {

    //storing accounts in a pre-determined, static file
    private static final File users = new File("users.txt");
    private static boolean loggedIn; //checks if a user is logged in
    private static Scanner sc = new Scanner(System.in);

    //adding an account into the database
    public static void addToTextDatabase(String filename, Account account, boolean append) {
        String username = account.getUsername();
        String password = account.getPassword();

        //writes "username: password" in the database
        FileUtilities.writeToFile(filename, username + ": " + password + "\n", append);
    }

    public static boolean checkUsername(String filename, String username) {
        String checkedUsername;

        //creates a new buffered reader for the file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            //reads the first line
            String line = br.readLine();

            //read while there is text in the file
            while (line != null) {
                //contains the first part of a line before ":" (the username)
                checkedUsername = line.split(":")[0].strip();
                //checks if the username matches a username in the database
                if (checkedUsername.equals(username)) {
                    return true;
                } else {
                    //continue reading
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if the inputted username doesn't match an existing one return false
        return false;
    }


    //method checking that the username matches the password
    public static boolean checkUsernameAndPassword(String filename, String username, String password){
        return FileUtilities.checkStringsTogether(filename, username, password);
    }
}
