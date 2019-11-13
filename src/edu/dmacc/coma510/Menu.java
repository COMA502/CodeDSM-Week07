package edu.dmacc.coma510;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {

    /**
     * NOTE the logic is all copied from week 6 assignment
     */
    private File file;
    private HashMap<String, Double> menu;

    public Menu(String meal) throws FileNotFoundException {
        file = new File("src/edu/dmacc/coma510/" + meal + "_menu.txt");
        menu = new HashMap<>();

        loadMenuFromFile();
    }

    private void loadMenuFromFile() throws FileNotFoundException {
        if (file.exists()) {
            try (Scanner menuScanner = new Scanner(file)) {
                while (menuScanner.hasNextLine()) {
                    String line = menuScanner.nextLine();
                    String[] split = line.split(",");
                    menu.put(split[0], Double.parseDouble(split[1]));
                }
            }
        }
    }

    private void saveMenuToFile() throws FileNotFoundException {
        try (PrintWriter menuWriter = new PrintWriter(file)) {
            for (String item : menu.keySet()) {
                menuWriter.println(item + "," + menu.get(item));
            }
        }
    }

    public void showMenu() {
        System.out.println("--- Menu ---");
        for (String item : menu.keySet()) {
            System.out.println(String.format("$%.2f\t", menu.get(item)) + item);
        }
    }

    public void addToMenu(String item, double price) throws FileNotFoundException {
        menu.put(item, price);

        saveMenuToFile();
    }

    public boolean hasFood(String food) {
        return menu.containsKey(food);
    }

    public double getPrice(String food) {
        return menu.get(food);
    }
}
