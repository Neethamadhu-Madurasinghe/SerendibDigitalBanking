package business_layer.notifications.handlers;

import business_layer.User;

public interface INotificationHandler {
    public void setNextHandler(INotificationHandler next);
    public boolean handleRequest(User user, String message);
}
