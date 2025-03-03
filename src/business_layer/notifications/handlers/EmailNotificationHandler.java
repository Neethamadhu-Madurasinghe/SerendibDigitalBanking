package business_layer.notifications.handlers;

import business_layer.User;
import libraries.EmailLibrary;

public class EmailNotificationHandler extends NotificationHandler{
    @Override
    public void setNextHandler(INotificationHandler next) {
        return;
    }

    @Override
    public boolean handleRequest(User user, String message) {
        boolean emailResult = false;
        if (user.getEmail() != null) {
            emailResult = EmailLibrary.sendEmail(user.getEmail(), message);
        }
        return emailResult;

    }
}
