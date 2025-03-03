package business_layer.notifications.service;

import business_layer.User;

public interface NotificationService {
    boolean sendNotification(User user, String message);
    boolean sendOtp(User user, String Otp);
}
