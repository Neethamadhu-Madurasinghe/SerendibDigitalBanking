package business_layer.notifications.service;

import business_layer.Customer;
import business_layer.notifications.handlers.*;


public class NotificationServiceImpl implements NotificationService {
    private OTPHandler otpHandler;
    private NotificationHandler notificationHandler;


    public NotificationServiceImpl() {
        OTPHandler emailOtpHandler = new EmailOTPHandler();
        OTPHandler smsOtpHandler = new SMSOTPHandler();
        OTPHandler authenticatorOtpHandler = new AuthenticatorOTPHandler();

        smsOtpHandler.setNextHandler(emailOtpHandler);
        emailOtpHandler.setNextHandler(authenticatorOtpHandler);
        this.otpHandler = smsOtpHandler;

        NotificationHandler emailNotificationHandler = new EmailNotificationHandler();
        NotificationHandler smsNotificationhandler = new SMSNotificationHandler();

        smsNotificationhandler.setNextHandler(emailNotificationHandler);
        this.notificationHandler = smsNotificationhandler;

    }


    @Override
    public boolean sendNotification(Customer customer, String message) {
        return this.notificationHandler.handleRequest(customer, message);
    }

    @Override
    public boolean sendOtp(Customer customer, String otp) {
        return this.otpHandler.handleRequest(customer, otp);
    }
}
