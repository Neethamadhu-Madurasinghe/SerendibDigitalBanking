package domain.services;

import domain.Customer;
import domain.notifications.AuthenticatorOTPHandler;
import domain.notifications.EmailOTPHandler;
import domain.notifications.OTPHandler;
import domain.notifications.SMSOTPHandler;


public class NotificationServiceImpl implements NotificationService {
    private OTPHandler otpHandler;


    public NotificationServiceImpl() {
        OTPHandler emailOtpHandler = new EmailOTPHandler();
        OTPHandler smsOtpHandler = new SMSOTPHandler();
        OTPHandler authenticatorOtpHandler = new AuthenticatorOTPHandler();

        smsOtpHandler.setNextHandler(emailOtpHandler);
        emailOtpHandler.setNextHandler(authenticatorOtpHandler);
        this.otpHandler = smsOtpHandler;
    }


    @Override
    public boolean sendNotification(Customer customer, String message) {
        return false;
    }

    @Override
    public boolean sendOtp(Customer customer, String otp) {
        return otpHandler.handleRequest(customer, otp);
    }
}
