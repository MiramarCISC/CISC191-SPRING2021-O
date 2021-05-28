package edu.sdccd.cisc191.o.client;

import edu.sdccd.cisc191.o.UI;

import java.util.Scanner;

public class App {
    //private UI appUI;



    /**
     * Method creates a new User called app User, sends it to Server to be stored on Server
     * @param scnr pass Scanner keyboard from main() into this argument
     */
    public static void createUser(Scanner scnr){
        String userName;
        String userPassword;
        String userPasswordVeri;    //password verification
        User appUser;

        System.out.println("Create a new user account");//replace this with a label in GUI
        System.out.println("Username: ");               //replace this with a label in GUI
        userName = scnr.nextLine();                     //put this in a text field in GUI
        System.out.println("Password: ");               //replace this with a label in GUI
        userPassword = scnr.nextLine();                 //put this in a text field in GUI
        System.out.println("Retype password: ");        //replace this with a label in GUI
        userPasswordVeri = scnr.nextLine();             //put this in a text field in GUI

        while (!userPassword.equals(userPasswordVeri)) {
            System.out.println("ERROR: your passwords are not the same, please retype your password."); //put this in a popup window

            System.out.println("Password: ");               //replace this with a label in GUI
            userPassword = scnr.nextLine();                 //put this in a text field in GUI
            System.out.println("Retype password: ");        //replace this with a label in GUI
            userPasswordVeri = scnr.nextLine();             //put this in a text field in GUI
        }
            System.out.println("Account created successfully");  //put this in a popup window
            System.out.println("Your username: " + userName);    //same popup window with the line above
            appUser = new User(userName,userPassword);
            //FIX ME: send appUser to Server to be stored in a User database
    }

    /**
     * Method sends appUser to Server for verification and receive reply from Server
     * @param scnr pass Scanner keyboard from main() into this argument
     */
    public static void signIn(Scanner scnr){
        String userName;
        String userPassword;
        User appUser;

        System.out.println("Sign in");                  //replace this with a label in GUI
        System.out.println("Username: ");               //replace this with a label in GUI
        userName = scnr.nextLine();                     //put this in a text field in GUI
        System.out.println("Password: ");               //replace this with a label in GUI
        userPassword = scnr.nextLine();                 //put this in a text field in GUI

        appUser = new User(userName,userPassword);
        //FIX ME: send appUser to Server for verification
        //in Server, search for matching username, then check if passwords match
    }


    //main()
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Sign in");                  //replace this with a button in GUI
        signIn(keyboard);

        System.out.println("Create new user account");  //replace this with a button in GUI
        createUser(keyboard);
    }
}
