package business_layer.notifications.service;

import business_layer.Customer;

public interface NotificationService {
    boolean sendNotification(Customer customer, String message);
    boolean sendOtp(Customer customer, String Otp);
}
