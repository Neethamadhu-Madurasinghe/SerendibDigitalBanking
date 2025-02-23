package domain.notifications;

import domain.OTPChannel;
import libraries.AuthenticatorLibrary;
import domain.Customer;

public class AuthenticatorOTPHandler extends OTPHandler {
    @Override
    public void setNextHandler(INotificationHandler next) {

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

