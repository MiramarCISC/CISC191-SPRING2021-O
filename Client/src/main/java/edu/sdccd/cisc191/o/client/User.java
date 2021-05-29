package edu.sdccd.cisc191.o.client;

import edu.sdccd.cisc191.o.DailyLog;
import edu.sdccd.cisc191.o.Request;

import java.net.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class contains members necessary to create a new user account, which also includes a DailyLog object.
 */
public class User {
    private String userName;
    private String password;
    private DailyLog dailyLog;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    //Default constructor
    public User() {
        userName = "userName";
        password = "password";
        dailyLog = null;
    }

    //Constructor
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        dailyLog = new DailyLog();
    }

    //Constructor
    public User(String userName, String password, DailyLog dailyLog) {
        this.userName = userName;
        this.password = password;
        this.dailyLog = dailyLog;
    }

    //Getter
    public String getUserName() {
        return userName;
    }

    //Setter
    public void setUserName(String userName) {
        this.userName = userName;
    }

    //Getter
    public String getPassword() {
        return password;
    }

    //Setter
    public void setPassword(String password) {
        this.password = password;
    }

    //Getter
    public DailyLog getDailyLog() {
        return dailyLog;
    }

    //Setter
    public void setDailyLog(DailyLog dailyLog) {
        this.dailyLog = dailyLog;
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
     * sendRequest() sends a request to Server and returns a DailyLog
     * @return DailyLog
     * @throws Exception
     */
    public DailyLog sendRequest() throws Exception {
        out.println(Request.toJSON(new Request(1)));    //outputting to server a JSON serialized  object of Request
        return DailyLog.fromJSON(in.readLine());    //outputting to server a JSON serialized  object of Request
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

/*  main() COMMENTED OUT (USED FOR TESTING)
    public static void main(String[] args) {
        User client = new User();
        try {
            client.startConnection("127.0.0.1", 8888);
            System.out.println(client.sendRequest().toString());
            client.logout();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
*/

    public void editLog(DailyLog dailyLog, LocalDate localDate, int dailyCalories, HashMap<String, Double> enteredIngredients, ArrayList<String> enteredFoods) {
        dailyLog.setLogDate(localDate);
        dailyLog.setDailyCalories(dailyCalories);
        dailyLog.setEnteredIngredients(enteredIngredients);
        dailyLog.setEnteredFoods(enteredFoods);
    }

    public void editLog(DailyLog dailyLog, LocalDate localDate, int dailyCalories) {
        dailyLog.setLogDate(localDate);
        dailyLog.setDailyCalories(dailyCalories);
    }

    public void editLog(DailyLog dailyLog, int dailyCalories) {
        dailyLog.setDailyCalories(dailyCalories);
    }

}
