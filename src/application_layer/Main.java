package application_layer;

import data_layer.casa.CASAAdapter;
import data_layer.casa.CASAInterface;
import business_layer.services.AuthService;
import business_layer.services.AuthServiceImpl;
import business_layer.notifications.service.NotificationService;
import business_layer.notifications.service.NotificationServiceImpl;
import business_layer.support.IDGenerator;
import data_layer.repository.UserRepository;
import data_layer.repository.UserRepositoryImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CASAInterface CASAAdapter = new CASAAdapter();
        NotificationService notificationService = NotificationServiceImpl.getInstance();
        IDGenerator idGenerator = IDGenerator.getInstance();
        UserRepository customerRepository = UserRepositoryImpl.getInstance();


        AuthService service = new AuthServiceImpl(CASAAdapter, notificationService, idGenerator, customerRepository);

        Scanner inputScanner = new Scanner(System.in);

        while(true) {
            System.out.println("============ Serendib Portal ============");
            System.out.println("1. Onboard");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            String input = inputScanner.nextLine();

            switch (input) {
                case "1":
                    service.onboard();
                    break;
                case "2":
                    service.login();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println(AnsiColors.RED + "Invalid input please select again" + AnsiColors.RESET);

            }
        }
    }
}