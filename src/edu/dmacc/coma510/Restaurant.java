package edu.dmacc.coma510;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Restaurant {

    /**
     * Using the restaurant example from lecture, you're going to add a couple classes. Hopefully, you'll see that
     * organizing into classes will help cleanup our main class! Cleaner code means it's also easier to maintain, so
     * this is a really important concept in the real World.
     *
     * Notice the Menu class that we created in lecture is already here for you to use.
     *
     * *Requirement #1 (worth 8 points):*
     * Create two new classes called Manager and Waitress. In those classes, create the following methods and use the
     * corresponding logic that currently exists in main. For example, in the Manager addToMenu method you can move the
     * logic from main for asking what food/price to add and calling menu.addToMenu(food, price).
     * 1. Manager
     * 1a. public void addToMenu(Menu menu, Scanner userInput) throws FileNotFoundException
     * 2. Waitress
     * 2a. public void takeOrder(Menu menu, Scanner userInput)
     * 2b. public void printTotal()
     *
     * *Requirement #2 (worth 4 points):*
     * The Waitress class must keep track of the order total. So, when items are ordered using Waitress takeOrder, then
     * Waitress printTotal should be able to use an instance variable to get the total to print. Your instance variable
     * must be initialized using a class constructor.
     *
     * *Requirement #3 (worth 8 points):*
     * After you move the logic to the new classes, you'll need to call the new class methods from main. When you run
     * main, the behavior should be exactly the same as before you started creating classes. For your convenience, I've
     * copied the instructions from the week 6 assignment below.
     *
     * -------------------------------------------------------------------
     * --------- Instructions copied from last week's assignment ---------
     * -------------------------------------------------------------------
     * How the application should operate:
     * When the application starts,
     * (1) load the menu from file 'menu.txt',
     * (2) print the menu to the user and
     * (3) ask the user, "Do you want to 'order' or 'add' to the menu?"
     *
     * If the user says 'add', then ask, "What food would you like to add?" And then ask, "What's the price?"
     * If the price entered isn't a number print, "The price must be a number." and ask again.
     * Repeat those two questions until the user enters 'done' for the food.
     * Then, print out the menu again, save the menu to file 'menu.txt' and end the application.
     *
     * If the user says 'order', then ask, "What food would you like?"
     * If the food entered is not on the menu print, "Sorry, we don't have "+food.
     * Repeat that question until the user enters 'done' for the food.
     * Print the user's total price of the order and end the application.
     *
     * Here's an example of running the application for the first time:
     * --- Menu ---
     *
     * Do you want to 'order' or 'add' to the menu? add
     * What food would you like to add? burger
     * What's the price? 1.5
     * What food would you like to add? fries
     * What's the price? a
     * The price must be a number.
     * What's the price? 2
     * What food would you like to add? done
     * --- Menu ---
     * $1.50	burger
     * $2.00	fries
     *
     * Here's an example of running the application another time:
     * --- Menu ---
     * $1.50	burger
     * $2.00	fries
     *
     * Do you want to 'order' or 'add' to the menu? order
     * What food would you like? burger
     * What food would you like? yogurt
     * Sorry, we don't have yogurt
     * What food would you like? fries
     * What food would you like? done
     * Your total is $3.50
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner userInput = new Scanner(System.in);

        System.out.print("\nDo you want to 'order' or 'add' to the menu? ");
        String operation = userInput.next();

        System.out.printf("Would you like 'breakfast' or 'dinner'? ");
        String meal = userInput.next();
        Menu menu = new Menu(meal);

        if (operation.equals("order")) {
            menu.showMenu();
            orderFood(menu, userInput);
        } else if (operation.equals("add")) {
            menu.showMenu();
            addToMenu(menu, userInput);
            menu.showMenu();
        } else {
            System.out.println(operation + " is not a valid option. Please enter 'order' or 'add' instead.");
        }
    }

    private static void orderFood(Menu menu, Scanner userInput) {
        boolean done = false;
        double total = 0;
        while (!done) {
            System.out.print("What food would you like; enter 'done' when complete? ");
            String food = userInput.next();
            if (food.equals("done")) {
                done = true;
            } else if (menu.hasFood(food)) {
                total += menu.getPrice(food);
            } else {
                System.out.println("Sorry, we don't have " + food);
            }
        }
        System.out.printf("Your total is $%.2f\n", total);
    }

    private static void addToMenu(Menu menu, Scanner userInput) throws FileNotFoundException {
        boolean done = false;
        while (!done) {
            System.out.print("What food would you like to add; enter 'done' when complete? ");
            String food = userInput.next();

            if (food.equals("done")) {
                done = true;
            } else {
                Double price = getPriceFromUser(userInput);
                menu.addToMenu(food, price);
            }
        }
    }

    private static Double getPriceFromUser(Scanner userInput) {
        try {
            System.out.print("What's the price? ");
            return userInput.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("The price must be a number.");
            userInput.next();//clear the input
            return getPriceFromUser(userInput);
        }
    }
}
