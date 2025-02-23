package business_layer.notifications.handlers;

import business_layer.Customer;
import libraries.EmailLibrary;

public class EmailNotificationHandler extends NotificationHandler{
    @Override
    public void setNextHandler(INotificationHandler next) {
        return;
    }

    @Override
    public boolean handleRequest(Customer customer, String message) {
        boolean emailResult = false;
        if (customer.getEmail() != null) {
            emailResult = EmailLibrary.sendEmail(customer.getEmail(), message);
        }
        return emailResult;

    }
}
