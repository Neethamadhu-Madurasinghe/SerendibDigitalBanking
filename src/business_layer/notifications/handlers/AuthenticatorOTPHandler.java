package business_layer.notifications.handlers;

import business_layer.OTPChannel;
import libraries.AuthenticatorLibrary;
import business_layer.User;

public class AuthenticatorOTPHandler extends OTPHandler {
    @Override
    public void setNextHandler(INotificationHandler next) {

    }

    @Override
    public boolean handleRequest(User user, String message) {
        if (user.getOtpChannel() == OTPChannel.AUTHENTICATOR_APP) {
            return AuthenticatorLibrary.sendOTP(user.getEmail(), message);

        } else {
            return false;
        }
    }
}

