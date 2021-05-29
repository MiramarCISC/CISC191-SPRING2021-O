package edu.sdccd.cisc191.o;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Author - Alex Nguyen
 * Co-Author - Sasanka P.
 *
 * This class represents has the attributes and the ability a user account has.
 * when creating a User object, a username and a password is assigned.
 *
 */
public class User implements Comparator<User> {
    private String userName;
    private String password;
    private History userHistory;
    private int logEntryNum = 0;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(User aUser) throws Exception {
        return objectMapper.writeValueAsString(aUser);
    }
    public static User fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, User.class);
    }

    //Default constructor
    public User() {
        userName = "userName";
        password = "password";
    }

    //Constructor
    public User(String param_userName, String param_password) {
        userName = param_userName;
        password = param_password;

        userHistory = new History();
    }

    //Setter
    public void setUserName(String param_userName) {
        userName = param_userName;
    }

    //Setter
    public void setPassword(String param_password) {
        password = param_password;
    }

    //Getter
    public String getUserName() {
        return userName;
    }

    //Getter
    public String getPassword() {
        return password;
    }



    public void addDailyLog(ArrayList<String> newFoods){
        logEntryNum++;
        DailyLog log = new DailyLog();
        log.setEnteredFoods(newFoods);
        log.setLogEntryNum(logEntryNum);
        userHistory.addEntry(log);
    }

    public int getTotalCalories(){
        return userHistory.getTotalCalories();
    }

    public int compare(User user1, User user2) {
        if(user1.getUserName().equals(user2.getUserName())){
            return 0;
        }else{
            return -1;
        }
    }

    /**
     * Author - Wolfgang Schrott
     *
     * The class used to store and iterate over and perform certain operations on a collection of DailyLog objects.
     * Used to represent the a User's log history.
     *
     * class localized and edited by Sasanka
     */
    public class History {
        private ArrayList<DailyLog> logHistory = new ArrayList<>();



        public DailyLog getLog(int entryNum){
            for(DailyLog log: logHistory){
                if(log.getLogEntryNum() == entryNum){
                    return log;
                }
            }
            return null;
        }

        public void addEntry(DailyLog log){
            logHistory.add(log);
        }

        public  int getTotalCalories(){
            int totalCalories = logHistory.stream().mapToInt(DailyLog::getDailyCalories).sum();
            return totalCalories;
        }

    }
}
