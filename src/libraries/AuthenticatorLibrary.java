package libraries;

import application_layer.AnsiColors;

public class AuthenticatorLibrary {
    public static boolean sendOTP(String emailAddress, String message) {
        System.out.println(AnsiColors.BLUE + "Authenticator OTP send to "  + emailAddress + ": " + message  + AnsiColors.RESET);
        return true;
    }
}
