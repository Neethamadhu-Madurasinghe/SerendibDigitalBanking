package business_layer.notifications.handlers;

import business_layer.User;
import business_layer.OTPChannel;
import libraries.SMSLibrary;

public class SMSOTPHandler extends OTPHandler {
    @Override
    public void setNextHandler(INotificationHandler next) {
        this.next = next;
    }

    @Override
    public boolean handleRequest(User user, String message) {
        if (user.getOtpChannel() == OTPChannel.SMS) {
            return SMSLibrary.sendSMS(user.getMobileNumber(), message);

        } else {
            boolean smsResult = false;
            if (user.getOtpChannel() == null && user.getMobileNumber() != null) {
                smsResult = SMSLibrary.sendSMS(user.getMobileNumber(), message);
            }
            return next.handleRequest(user, message) || smsResult;
        }

    }
}
