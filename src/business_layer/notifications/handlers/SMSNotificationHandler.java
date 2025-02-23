package business_layer.notifications.handlers;

import business_layer.Customer;
import libraries.SMSLibrary;

public class SMSNotificationHandler extends NotificationHandler {
    @Override
    public void setNextHandler(INotificationHandler next) {
        this.next = next;
    }

    @Override
    public boolean handleRequest(Customer customer, String message) {
        boolean smsResult = false;
        if (customer.getMobileNumber() != null) {
            smsResult = SMSLibrary.sendSMS(customer.getMobileNumber(), message);
        }
        return next.handleRequest(customer, message) || smsResult;

    }
}

