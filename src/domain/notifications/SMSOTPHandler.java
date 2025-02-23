package domain.notifications;

import domain.Customer;
import domain.OTPChannel;
import libraries.SMSLibrary;

public class SMSOTPHandler extends OTPHandler {
    @Override
    public void setNextHandler(INotificationHandler next) {
        this.next = next;
    }

    @Override
    public boolean handleRequest(Customer customer, String message) {
        if (customer.getOtpChannel() == OTPChannel.SMS) {
            return SMSLibrary.sendSMS(customer.getMobileNumber(), message);

        } else {
            boolean smsResult = false;
            if (customer.getMobileNumber() != null) {
                smsResult = SMSLibrary.sendSMS(customer.getMobileNumber(), message);
            }
            return next.handleRequest(customer, message) || smsResult;
        }

    }
}
