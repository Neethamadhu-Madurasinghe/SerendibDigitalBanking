package domain.services;

import application.AnsiColors;
import domain.Customer;
import domain.CustomerBuilder;
import domain.Language;
import domain.casa.CASAInterface;

import java.util.Scanner;

public class ServiceImpl implements Service {
    CustomerBuilder customerBuilder;
    CASAInterface CASASystem;

    public ServiceImpl(CustomerBuilder builder, CASAInterface CASASystem) {
        this.customerBuilder = builder;
        this.CASASystem = CASASystem;
    }

    @Override
    public Customer onboard() {
        Scanner inputScanner = new Scanner(System.in);
        boolean loopInput = true;
        String input = "";

        System.out.println(AnsiColors.YELLOW + "=============== Registering customer ==============" + AnsiColors.RESET);

        while(loopInput) {
            System.out.println("Select your language\n1.English\n2.Sinhala\n");
            input = inputScanner.nextLine();

            switch (input) {
                case "1":
                    customerBuilder.language(Language.ENGLISH);
                    loopInput = false;
                    break;
                case "2":
                    customerBuilder.language(Language.SINHALA);
                    loopInput = false;
                    break;
                default:
                    System.out.println(AnsiColors.RED + "Invalid input please select again" + AnsiColors.RESET);

            }
        }

        loopInput = true;

        while(loopInput) {
            System.out.println("Enter your NIC/Passport Number: ");
            input = inputScanner.nextLine();

//            Validation
            if (input.length() < 5) {
                customerBuilder.nicPassport(input);
                loopInput = false;

            } else {
                System.out.println(AnsiColors.RED + "NIC/Passport validation failed" + AnsiColors.RESET);
            }
        }

        loopInput = true;

        while(loopInput) {
            System.out.println("Enter your CASA Acc. Number: ");
            input = inputScanner.nextLine();

//            Validation
            if (input.length() < 5) {
                try {
                    String name = CASASystem.getCustomerNameByAccountNumber(input);
                    customerBuilder.name(name);

                } catch (Exception e) {
                    System.out.println(AnsiColors.RED + "No such user !!" + AnsiColors.RESET);
                }

            } else {
                System.out.println(AnsiColors.RED + "CASA Acc no. validation failed" + AnsiColors.RESET);
            }
        }

        loopInput = true;


        return null;
    }

    @Override
    public Customer login() {
        return null;
    }
}
