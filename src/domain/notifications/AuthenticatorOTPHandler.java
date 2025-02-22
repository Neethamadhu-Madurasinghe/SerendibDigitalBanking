package domain.notifications;

import application.AnsiColors;
import domain.OTPChannel;
import libraries.AuthenticatorLibrary;
import domain.Customer;

public class AuthenticatorOTPHandler extends OTPHandler {
    @Override
    public void setNextHandler(NotificationHandler next) {

    }

    @Override
    public boolean handleRequest(Customer customer, String message) {
        if (customer.getOtpChannel() == OTPChannel.AUTHENTICATOR_APP) {
            return AuthenticatorLibrary.sendOTP(customer.getEmail(), message);

        } else {
            return false;
        }
    }
}

