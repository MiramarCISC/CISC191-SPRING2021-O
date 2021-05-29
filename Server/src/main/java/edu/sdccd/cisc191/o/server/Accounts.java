package edu.sdccd.cisc191.o.server;

import edu.sdccd.cisc191.o.DailyLog;
import edu.sdccd.cisc191.o.Request;
import edu.sdccd.cisc191.o.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Accounts {

    private ArrayList<User> userAccounts = new ArrayList<>();


    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws Exception {
        userAccounts.add(new User("JohnDoe", "password123"));
        userAccounts.add(new User("JaneDoe", "password123"));

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            Request request = Request.fromJSON(inputLine);
            User user = getUser(request.getLogEntryDay());
            out.println(User.toJSON(user));
        }
    }


    public User getUser(String User){
        //For now add an empty dailylog to logHistory
        for(User user: userAccounts) {
            if(user.getUserName().equals(User)) {
                return user;
            }
        }
        return null;
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }


    public static void main(String[] args) {
        Accounts server = new Accounts();
        try {
            server.start(8888);
            server.stop();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
