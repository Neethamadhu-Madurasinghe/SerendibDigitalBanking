package domain.services;

import domain.Customer;

public interface NotificationService {
    boolean sendNotification(Customer customer, String message);
    boolean sendOtp(Customer customer, String Otp);
}
