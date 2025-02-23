package domain.notifications;

import domain.Customer;

public interface INotificationHandler {
    public void setNextHandler(INotificationHandler next);
    public boolean handleRequest(Customer customer, String message);
}
