import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    // initialize the array list used for the menu
    static ArrayList<String> menuArrayList = new ArrayList<>();
    // initialize the scanner for user input
    static Scanner in = new Scanner(System.in);
    // initialize variable to check state of list if it is saved/not saved
    static boolean saved = false;
    public static void main(String[] args) {

        // initialize variable for quitting/continuing
        boolean exitProgram = false;
        // loop continuously until user quits
        while(!exitProgram) {
            displayMenu(); // display the menu
            // ask user for input of menu selection
            String choice = SafeInput.getRegExString(in, "Please enter your menu selection (A, D, V, Q, O, S, C): ","[AaDdVvQqOoSsCc]");
            // switch case structure based on user input to select menu option, will run desired method
            switch (choice.toUpperCase()) {
                case "A": // if user enters A or a, it will allow user to add to list or array
                    addItem();
                    break;
                case "D": // if user enters D or d, it will allow user to delete item from list or array
                    deleteItem();
                    break;
                case "V": // if user enters V or v, it will print out list/array
                    viewList();
                    break;
                case "Q": // if user enters Q or q, it will ask to confirm their exit
                    exitProgram = confirmExit();
                    break;
                case "O": // if user enters O or o, it will open the list file from disk
                    openListFile();
                    break;
                case "S": // if user enters S or s, it will save the list file to disk
                //    saveListFile();
                    break;
                case "C": // if user enters C or c, it will erase all elements from the current list
                    removeAllElements(menuArrayList);
                    break;
                default: // default case in event user input is invalid and safe input methods do not catch invalid inputs as intended
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        // print out the menu options for user to select from
        System.out.println("Menu:");
        System.out.println("A - Add an item to the array/list");
        System.out.println("D - Delete an item from the array/list");
        System.out.println("V - View the array/list");
        System.out.println("Q - Quit/Exit the program");
        System.out.println("O - Open list/array from disk");
        System.out.println("S - Save list/array to disk");
        System.out.println("C - Clear all elements from current list/array");
    }

    // method to add item to list/array
    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter an item to add");
        menuArrayList.add(item);
        System.out.println("Item added: " + item);
    }

    // method to delete item from array/list
    private static void deleteItem() {
        if (menuArrayList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        System.out.println("Current list:");
        displayNumberedItems();
        int index = SafeInput.getRangedInt(in,"Enter the number of the item to delete: ", 1, menuArrayList.size());
        String item = menuArrayList.remove(index - 1);
        System.out.println("Item deleted: " + item);
    }


    // method to print out list
    private static void viewList() {
        System.out.println("Current list/array:");
        displayNumberedItems();
    }

    // method to confirm user wanting to exit program
    private static boolean confirmExit() {
        // returns the user input of True (Yes) or False (No)
        return SafeInput.getYNConfirm(in,"Are you sure you want to quit? (Y/N): ");
    }

    // method to display the array as number list
    private static void displayNumberedItems() {
        // iterates through array and numbers the elements and prints out a numbered list of elements
        for (int i = 0; i < menuArrayList.size(); i++) {
            System.out.println((i + 1) + ". " + menuArrayList.get(i));
        }
    }

    // method to open list
    private static void openListFile() {
        if (saved) {
            String prompt = "Would you like to open a new list?";
            boolean deleteListYN = SafeInput.getYNConfirm(in, prompt);
            if (!deleteListYN) {
                return;
            }
        }

        Scanner inFile;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        chooser.setFileFilter(filter);
        String line;

        Path target = Paths.get(System.getProperty("user.dir")).resolve("src");
        chooser.setCurrentDirectory(target.toFile());

        try {
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                target = chooser.getSelectedFile().toPath();
                inFile = new Scanner(target);
                System.out.println("Opened " + target.getFileName());
                while (inFile.hasNextLine()) {
                    line = inFile.nextLine();
                    menuArrayList.add(line);
                }
                inFile.close();
            } else { // user did not select a file
                System.out.println("Please select a new file");
            }
        } catch (IOException e) {
            System.out.println("IOException Error");
        }
    }

    // method to clear all elements from current list
    private static void removeAllElements(ArrayList<String> arrayList) {
        arrayList.clear(); // clear out array/list
        System.out.println("All elements erased from current list/array."); // print out that all elements were erased
    }


}