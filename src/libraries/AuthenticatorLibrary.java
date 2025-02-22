package libraries;

import application.AnsiColors;

public class AuthenticatorLibrary {
    public static boolean sendOTP(String emailAddress, String message) {
        System.out.println(AnsiColors.PURPLE + "OTP: " + message + " sent to " + emailAddress + AnsiColors.RESET);
        return true;
    }
}
