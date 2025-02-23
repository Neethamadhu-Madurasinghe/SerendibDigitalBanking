package libraries;

import application_layer.AnsiColors;

public class EmailLibrary {
    public static boolean sendEmail(String emailAddress, String message) {
        System.out.println(AnsiColors.BLUE + "Email send to "  + emailAddress + ": " + message  + AnsiColors.RESET);
        return true;
    }
}
