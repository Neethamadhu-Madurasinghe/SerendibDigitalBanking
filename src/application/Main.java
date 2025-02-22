package application;

import domain.casa.CASAAdapter;
import domain.casa.CASAInterface;
import domain.services.AuthService;
import domain.services.AuthServiceImpl;
import domain.services.NotificationService;
import domain.services.NotificationServiceImpl;
import domain.support.IDGenerator;

public class Main {
    public static void main(String[] args) {
        CASAInterface CASAAdapter = new CASAAdapter();
        NotificationService notificationService = new NotificationServiceImpl();
        IDGenerator idGenerator = IDGenerator.getInstance();


        AuthService service = new AuthServiceImpl(CASAAdapter, notificationService, idGenerator);
        service.onboard();
    }
}