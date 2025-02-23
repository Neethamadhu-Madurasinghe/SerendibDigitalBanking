package business_layer.notifications.handlers;

import business_layer.Customer;
import business_layer.OTPChannel;
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
            if (customer.getOtpChannel() == null && customer.getMobileNumber() != null) {
                smsResult = SMSLibrary.sendSMS(customer.getMobileNumber(), message);
            }
            return next.handleRequest(customer, message) || smsResult;
        }

    }
}
