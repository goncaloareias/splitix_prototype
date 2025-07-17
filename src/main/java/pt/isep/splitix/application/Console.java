package pt.isep.splitix.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for console input operations.
 * Provides methods to read various types of inputs from the command line.
 */
public class Console {

    /**
     * Reads a line of text from the console after printing a prompt.
     *
     * @param prompt the message shown to the user before input
     * @return the user input as a String, or null if an error occurred
     */
    public static String readLine(String prompt) {
        try {
            System.out.println(prompt);
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            return in.readLine();
        } catch (Exception ex1) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
                    null, ex1);
            return null;
        }

    }

    /**
     * Reads an integer from the console after prompting.
     * Repeats until a valid integer is entered.
     *
     * @param prompt the message shown to the user
     * @return the parsed integer value
     */
    public static int readInteger(String prompt) {
        do {
            try {
                String strInt = readLine(prompt);
                int valor = Integer.parseInt(strInt);

                return valor;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
                        null, ex);
            }

        } while (true);
    }

    /**
     * Reads a boolean from the console after prompting.
     * Accepts 'y' or 's' (yes) as true, 'n' as false.
     * Repeats until a valid input is entered.
     *
     * @param prompt the message shown to the user
     * @return true if user inputs 'y' or 's', false if 'n'
     */
    public static boolean readBoolean(String prompt) {
        do {
            try {
                String strBool = readLine(prompt).toLowerCase();
                if (strBool.equals("y") || strBool.equals("s")) {
                    return true;
                } else if (strBool.equals("n")) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
                        null, ex);
            } catch (Exception ex1) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
                        null, ex1);
            }
        } while (true);
    }

    /**
     * Reads an option integer within a specified range from the console.
     * User may also enter the exit code to break out.
     *
     * @param low  lowest acceptable option
     * @param high highest acceptable option
     * @param exit integer value that exits input loop
     * @return the chosen option or exit value
     */
    public static int readOption(int low, int high, int exit) {
        int option;
        do {
            option = Console.readInteger("Introduza opção: ");
            if (option == exit) {
                break;
            }
        } while (option < low || option > high);
        return option;
    }
    /**
     * Reads a double value from the console after prompting.
     * Repeats until a valid double is entered.
     *
     * @param prompt the message shown to the user
     * @return the parsed double value
     */
    public static double readDouble(String prompt) {
        do {
            try {
                String input = readLine(prompt);
                double valor = Double.parseDouble(input);

                return valor;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
                        null, ex);
            } catch (Exception ex1) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
                        null, ex1);
            }
        } while (true);
    }

    /**
     * Pauses the program waiting for the user to press any key.
     *
     * @param prompt message to display before waiting
     */
    public static void waitForKey(String prompt) {
        System.out.println(prompt);
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    private Console() {

    }
}

