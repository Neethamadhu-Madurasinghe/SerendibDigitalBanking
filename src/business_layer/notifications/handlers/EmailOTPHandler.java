package business_layer.notifications.handlers;

import business_layer.User;
import business_layer.OTPChannel;
import libraries.EmailLibrary;

public class EmailOTPHandler extends OTPHandler {
    @Override
    public void setNextHandler(INotificationHandler next) {
        this.next = next;
    }

    @Override
    public boolean handleRequest(User user, String message) {
        if (user.getOtpChannel() == OTPChannel.EMAIL) {
            return EmailLibrary.sendEmail(user.getEmail(), message);

        } else {
            boolean emailResult = false;
            if (user.getOtpChannel() == null && user.getEmail() != null) {
                emailResult = EmailLibrary.sendEmail(user.getEmail(), message);
            }
            return next.handleRequest(user, message) || emailResult;
        }

    }
}
