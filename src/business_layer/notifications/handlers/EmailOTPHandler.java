package business_layer.notifications.handlers;

import business_layer.Customer;
import business_layer.OTPChannel;
import libraries.EmailLibrary;

public class EmailOTPHandler extends OTPHandler {
    @Override
    public void setNextHandler(INotificationHandler next) {
        this.next = next;
    }

    @Override
    public boolean handleRequest(Customer customer, String message) {
        if (customer.getOtpChannel() == OTPChannel.EMAIL) {
            return EmailLibrary.sendEmail(customer.getEmail(), message);

        } else {
            boolean emailResult = false;
            if (customer.getOtpChannel() == null && customer.getEmail() != null) {
                emailResult = EmailLibrary.sendEmail(customer.getEmail(), message);
            }
            return next.handleRequest(customer, message) || emailResult;
        }

    }
}
