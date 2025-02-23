package business_layer.notifications.handlers;

import business_layer.OTPChannel;
import libraries.AuthenticatorLibrary;
import business_layer.Customer;

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

