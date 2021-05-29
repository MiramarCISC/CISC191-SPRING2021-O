package edu.sdccd.cisc191.o;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.time.LocalDate;

/**
 * Author - Sasanka P.
 * Co-Authors -  Alex Nguyen & Andrew Wilson
 *
 *
 * This class is used to create an object DailyLog
 * The class also includes methods to JSON serialize an object of DailyLog
 * Likewise, it also has a method to deserialize a JSON serialized DailyLog object
 *
 */
public class DailyLog {
    private int logEntryNum;
    private LocalDate logDate;
    private int dailyCalories = 0;
    ArrayList<String> enteredFoods;       //String or Food?

    //Default constructor
    public DailyLog() {
        this.logDate = LocalDate.now();
        this.dailyCalories = 0;
        this.enteredFoods = null;
    }

    //Constructor
    public DailyLog(LocalDate logDate, ArrayList<String> enteredFoods) {
        this.logDate = logDate;
        this.dailyCalories = 0;
        this.enteredFoods = enteredFoods;

        for(String food: enteredFoods){
            searchFoods(food);
        }
    }



    public void searchFoods(String input){
        try {
            Database loadFile = new Database();
            int calories = loadFile.searchFood(input);
            dailyCalories += calories;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int getDailyCalories(){
        return dailyCalories;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogEntryNum(int entry) {
        this.logEntryNum = entry;
    }

    public int getLogEntryNum() {
        return logEntryNum;
    }

    public void setDailyCalories(int dailyCalories) {
        this.dailyCalories = dailyCalories;
    }


    public void setEnteredFoods(ArrayList<String> enteredFoods) {
        this.enteredFoods = enteredFoods;

        for(String food: enteredFoods){
            searchFoods(food);
        }
    }

    @Override
    public String toString() {
        return String.format("Daily Calorie amount is %d on %s",dailyCalories, getLogDate());
    }

}
