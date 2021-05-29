package edu.sdccd.cisc191.o.client;

import edu.sdccd.cisc191.o.Request;
import edu.sdccd.cisc191.o.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    /**
     * Method creates a new User called app User, sends it to Server to be stored on Server
     * @param scnr pass Scanner keyboard from main() into this argument
     */
    public static User createUser(Scanner scnr){
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
        return appUser;
    }


    /**
     * startConnection() creates a connection with Server
     * @param ip server ip
     * @param port use 8888
     * @throws IOException
     */
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);    //Client Socket created to given port & ip
        out = new PrintWriter(clientSocket.getOutputStream(), true);    //output from client to the other end of socket(server)
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  //input from the other end of the socket(server)
    }

    /**
     *
     * @param username the username of the account requested from the server
     * @return the user account associated with the username
     * @throws Exception
     */
    public User sendRequest(String username) throws Exception {
        out.println(Request.toJSON(new Request(username)));    //outputting to server a JSON serialized  object of Request
        return User.fromJSON(in.readLine());    //outputting to server a JSON serialized  object of Request
    }

    /**
     * Method sends appUser to Server for verification and receive reply from Server
     * @param scnr pass Scanner keyboard from main() into this argument
     */
    public static void signIn(Scanner scnr, App CalorieApp){
        String userName;
        String userPassword;
        User appUser;

        System.out.println("Sign in");                  //replace this with a label in GUI
        System.out.println("Username: ");               //replace this with a label in GUI
        userName = scnr.nextLine();                     //put this in a text field in GUI
        System.out.println("Password: ");               //replace this with a label in GUI
        userPassword = scnr.nextLine();                 //put this in a text field in GUI

        try {
            appUser = CalorieApp.sendRequest(userName);
            if(appUser.getPassword().equals(userPassword)){
                System.out.println("Welcome back " +appUser.getUserName());
                System.out.println("Enter a list of foods to add to a daily log, enter -1 after the last item");
                ArrayList<String> foods = new ArrayList<String>();
                String food = scnr.nextLine();
                while(!food.equals("-1")) {
                    foods.add(food);
                    food = scnr.nextLine();
                }
                    appUser.addDailyLog(foods);
                System.out.println("Total Calories of all the entered logs: " + appUser.getTotalCalories());
            }else{
                System.out.println(" User does not exist ");
            }
        }catch (Exception e){

        }
        //FIX ME: send appUser to Server for verification
        //in Server, search for matching username, then check if passwords match
    }

    /**
     * logout() closes all connections
     * @throws IOException
     */
    public void logout() throws IOException {
        //Close all the I/O streams and disconnect clientSocket
        in.close();
        out.close();
        clientSocket.close();
    }

    //main()
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        App calorieApp = new App();
        try {
            calorieApp.startConnection("127.0.0.1", 8888);
            signIn(keyboard, calorieApp);
            calorieApp.logout();
        } catch(Exception e) {
            e.printStackTrace();
        }

        //for demonstration
        User newUser = calorieApp.createUser(keyboard);


        System.out.println("New User created \n Enter a list of foods to add to a daily log, enter -1 after the last item");
        ArrayList<String> foods = new ArrayList<String>();
        String food = keyboard.nextLine();
        while(!food.equals("-1")) {
            foods.add(food);
            food = keyboard.nextLine();
        }
        newUser.addDailyLog(foods);
        System.out.println("Total Calories of all the entered logs: " + newUser.getTotalCalories());

        //

    }
}
