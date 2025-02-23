package domain.notifications;

public abstract class NotificationHandler implements INotificationHandler {
    protected INotificationHandler next;
}
