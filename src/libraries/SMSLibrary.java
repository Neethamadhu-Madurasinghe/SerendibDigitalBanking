package libraries;

import application_layer.AnsiColors;

public class SMSLibrary {
    public static boolean sendSMS(String phoneNumber, String message) {
        System.out.println(AnsiColors.BLUE + "SMS send to "  + phoneNumber + ": " + message  + AnsiColors.RESET);
        return true;
    }
}
