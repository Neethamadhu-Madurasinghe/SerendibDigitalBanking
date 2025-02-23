package business_layer.notifications.handlers;

public abstract class NotificationHandler implements INotificationHandler {
    protected INotificationHandler next;
}
