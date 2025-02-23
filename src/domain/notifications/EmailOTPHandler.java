package domain.notifications;

import domain.Customer;
import domain.OTPChannel;
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
            if (customer.getEmail() != null) {
                emailResult = EmailLibrary.sendEmail(customer.getEmail(), message);
            }
            return next.handleRequest(customer, message) || emailResult;
        }

    }
}
