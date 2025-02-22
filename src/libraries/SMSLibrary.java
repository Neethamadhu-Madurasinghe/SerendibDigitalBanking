package libraries;

import application.AnsiColors;

public class SMSLibrary {
    public static boolean sendSMS(String phoneNumber, String message) {
        System.out.println(AnsiColors.GREEN + "SMS: " + message + " sent to " + phoneNumber + AnsiColors.RESET);
        return true;
    }
}
