package business_layer.notifications.service;

import business_layer.User;
import business_layer.notifications.handlers.*;


public class NotificationServiceImpl implements NotificationService {
    private OTPHandler otpHandler;
    private NotificationHandler notificationHandler;
    private static NotificationServiceImpl instance = null;


//    Thread safe singleton
    public static NotificationService getInstance() {
        if (instance == null) {
            synchronized (NotificationServiceImpl.class) {
                if (instance == null) {
                    instance = new NotificationServiceImpl();
                }
            }
        }

        return instance;
    }


    private NotificationServiceImpl() {
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
    public boolean sendNotification(User user, String message) {
        return this.notificationHandler.handleRequest(user, message);
    }

    @Override
    public boolean sendOtp(User user, String otp) {
        return this.otpHandler.handleRequest(user, otp);
    }
}
