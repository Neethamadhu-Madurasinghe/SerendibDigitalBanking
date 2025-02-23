package domain.notifications;

public abstract class OTPHandler implements INotificationHandler {
    protected INotificationHandler next;
}
