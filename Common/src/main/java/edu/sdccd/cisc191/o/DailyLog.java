package edu.sdccd.cisc191.o;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import  edu.sdccd.cisc191.o.Food;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.time.LocalDate;

/**
 * This class is used to create an object DailyLog
 * The class also includes methods to JSON serialize an object of DailyLog
 * Likewise, it also has a method to deserialize a JSON serialized DailyLog object
 *
 */
public class DailyLog implements Comparator<DailyLog> {
    LocalDate logDate;
    private int dailyCalories = 0;
    HashMap<String, Double> enteredIngredients; //String or Ingredient?
    ArrayList<String> enteredFoods;       //String or Food?

    //Default constructor
    public DailyLog() {
        this.logDate = LocalDate.now();
        this.dailyCalories = 0;
        this.enteredIngredients = null;
        this.enteredFoods = null;
    }

    //Constructor
    public DailyLog(LocalDate logDate, int dailyCalories, HashMap<String, Double> enteredIngredients, ArrayList<String> enteredFoods) {
        this.logDate = logDate;
        this.dailyCalories = dailyCalories;
        this.enteredIngredients = enteredIngredients;
        this.enteredFoods = enteredFoods;
    }



    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(DailyLog log) throws Exception {
        return objectMapper.writeValueAsString(log);
    }
    public static DailyLog fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, DailyLog.class);
    }

    public void searchFoods(String input){
        try {
            Database loadFile = new Database();
            int calories = loadFile.searchFood(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int getDailyCalories(){
        //if its foods use searchFoods() and return dailyCalories
        //else
        //use ingredients use searchByIngredients() and return dailyCalories
       
        return dailyCalories;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public void setDailyCalories(int dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    @Override
    public String toString() {
        return String.format("Daily Calorie amount is %d because you starved today",dailyCalories);
    }

    @Override
    public int compare(DailyLog log1, DailyLog log2) {
        return Integer.compare(log1.getDailyCalories(), log2.getDailyCalories());
    }
}
