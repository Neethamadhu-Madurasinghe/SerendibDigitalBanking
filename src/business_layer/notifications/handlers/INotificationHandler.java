package business_layer.notifications.handlers;

import business_layer.Customer;

public interface INotificationHandler {
    public void setNextHandler(INotificationHandler next);
    public boolean handleRequest(Customer customer, String message);
}
