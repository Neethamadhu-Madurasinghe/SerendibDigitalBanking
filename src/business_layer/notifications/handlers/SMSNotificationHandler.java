package business_layer.notifications.handlers;

import business_layer.User;
import libraries.SMSLibrary;

public class SMSNotificationHandler extends NotificationHandler {
    @Override
    public void setNextHandler(INotificationHandler next) {
        this.next = next;
    }

    @Override
    public boolean handleRequest(User user, String message) {
        boolean smsResult = false;
        if (user.getMobileNumber() != null) {
            smsResult = SMSLibrary.sendSMS(user.getMobileNumber(), message);
        }
        return next.handleRequest(user, message) || smsResult;

    }
}

