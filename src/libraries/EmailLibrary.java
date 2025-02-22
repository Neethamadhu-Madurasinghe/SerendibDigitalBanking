package libraries;

import application.AnsiColors;

public class EmailLibrary {
    public static boolean sendEmail(String emailAddress, String message) {
        System.out.println(AnsiColors.BLUE + "Email: " + message + " sent to " + emailAddress + AnsiColors.RESET);
        return true;
    }
}
