package domain.notifications;

import domain.Customer;

public interface NotificationHandler {
    public void setNextHandler(NotificationHandler next);
    public boolean handleRequest(Customer customer, String message);
}
