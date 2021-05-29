package edu.sdccd.cisc191.o;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Database {

    private final Map<String, List<Integer>> foods;
    private final static String NUTRITION_FILE = "Common/src/nutrition.csv";

    /**
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Database() throws FileNotFoundException, IOException {
        foods = new HashMap<>();
        readNutritionFile();
    }

    /**
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void readNutritionFile() throws FileNotFoundException, IOException {

        BufferedReader in = new BufferedReader(new FileReader(NUTRITION_FILE));
        String line;

        while ((line = in.readLine()) != null) {
            String columns[] = line.split(",");
            String key = columns[1];
            int valueInt;
            List<Integer> valueList;

            try {
                valueInt = Integer.parseInt(columns[3]);
            } catch (NumberFormatException e) {
                System.out.println(e);
                continue;
            }

            if (foods.containsKey(key)) {
                valueList = foods.get(key);
            } else {
                valueList = new ArrayList<>();
                foods.put(key, valueList);
            }

            valueList.add(valueInt);
        }

        in.close();
    }

    public int searchFood(String input) {
        if (foods.containsKey(input))
            return foods.get(input).get(0);
        else {
            return -1;
        }

    }
}