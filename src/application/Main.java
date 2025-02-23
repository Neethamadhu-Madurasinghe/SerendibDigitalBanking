package application;

import domain.Language;
import domain.casa.CASAAdapter;
import domain.casa.CASAInterface;
import domain.services.AuthService;
import domain.services.AuthServiceImpl;
import domain.services.NotificationService;
import domain.services.NotificationServiceImpl;
import domain.support.IDGenerator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CASAInterface CASAAdapter = new CASAAdapter();
        NotificationService notificationService = new NotificationServiceImpl();
        IDGenerator idGenerator = IDGenerator.getInstance();


        AuthService service = new AuthServiceImpl(CASAAdapter, notificationService, idGenerator);

        Scanner inputScanner = new Scanner(System.in);

        while(true) {
            System.out.println("============ Serendib Portal ============");
            System.out.println("1. Onboard");
            System.out.println("2. Login");

            String input = inputScanner.nextLine();

            switch (input) {
                case "1":
                    service.onboard();
                    break;
                case "2":
                    service.login();
                    break;
                default:
                    System.out.println(AnsiColors.RED + "Invalid input please select again" + AnsiColors.RESET);

            }
        }
    }
}