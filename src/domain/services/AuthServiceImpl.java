package domain.services;

import application.AnsiColors;
import domain.Customer;
import domain.Language;
import domain.casa.CASAInterface;
import domain.casa.CustomerData;
import domain.support.IDGenerator;
import domain.verification.VerifyUsingBranch;
import domain.verification.VerifyUsingCallCentre;

import java.util.Scanner;

public class AuthServiceImpl implements AuthService {
    private CASAInterface CASASystem;
    private NotificationService notificationService;
    private IDGenerator idGenerator;

    public AuthServiceImpl( CASAInterface CASASystem, NotificationService notificationService, IDGenerator idGenerator) {
        this.CASASystem = CASASystem;
        this.notificationService = notificationService;
        this.idGenerator = idGenerator;
    }

    @Override
    public Customer onboard() {
        Scanner inputScanner = new Scanner(System.in);
        boolean loopInput = true;
        String input = "";
        Customer newCustomer = new Customer(idGenerator.nextId());

        System.out.println(AnsiColors.YELLOW + "=============== Registering customer ==============" + AnsiColors.RESET);

        while(loopInput) {
            System.out.println("Select your language\n1. English\n2. Sinhala\n");
            input = inputScanner.nextLine();

            switch (input) {
                case "1":
                    newCustomer.setLanguage(Language.ENGLISH);
                    loopInput = false;
                    break;
                case "2":
                    newCustomer.setLanguage(Language.SINHALA);
                    loopInput = false;
                    break;
                default:
                    System.out.println(AnsiColors.RED + "Invalid input please select again" + AnsiColors.RESET);

            }
        }

        System.out.println("You selected " + newCustomer.getLanguage());

        loopInput = true;

        while(loopInput) {
            System.out.println("Enter your NIC/Passport Number: ");
            input = inputScanner.nextLine();

//            Validation
            if (input.length() >= 5) {
                newCustomer.setNicPassportNumber(input);
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
            if (input.length() >= 5) {
                try {
                    CustomerData data = CASASystem.getCustomerDataByAccountNumber(input);
                    newCustomer.setName(data.name());
                    newCustomer.setMobileNumber(data.phoneNumber());
                    newCustomer.setEmail(data.email());
                    loopInput = false;

                } catch (Exception e) {
                    System.out.println(AnsiColors.RED + "No such user !!" + AnsiColors.RESET);
                }

            } else {
                System.out.println(AnsiColors.RED + "CASA Acc no. validation failed" + AnsiColors.RESET);
            }
        }

        loopInput = true;

//        OTP
        String otp = "1234";
        int otpAttempts = 3;

        while (otpAttempts > 0) {
            System.out.println(AnsiColors.GREEN + "Sending OTP for the " + (3 - otpAttempts + 1) + " time. (" + otp + ")" + AnsiColors.RESET);
            notificationService.sendOtp(newCustomer, otp);

            loopInput = true;
            while(loopInput) {
                System.out.println("Enter OTP: ");
                System.out.println("Select your language\n1. Enter correct OTP\n2. Request new OTP\n3. Enter incorrect OTP\n");
                input = inputScanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("OTP is Correct");
                        loopInput = false;
                        otpAttempts = 0;
                        break;
                    case "2":
                        otpAttempts--;
                        if (otpAttempts >= 1) {
                            System.out.println("Requesting OTP again");
                            loopInput = false;
                        } else {
                            System.out.println(AnsiColors.RED + "Registration failed: Locking the account" + AnsiColors.RESET);
                            return null;
                        }
                        break;
                    default:
                        System.out.println(AnsiColors.RED + "Invalid OTP please try again" + AnsiColors.RESET);

                }
            }

        }

        loopInput = true;

//        Verification

        while(loopInput) {
            System.out.println("Select account verification method\n1. Contact Call Centre\n2. At a Serendib Branch\n");
            input = inputScanner.nextLine();

            switch (input) {
                case "1":
                    newCustomer.setVerificationStrategy(new VerifyUsingCallCentre());
                    loopInput = false;
                    break;
                case "2":
                    newCustomer.setVerificationStrategy(new VerifyUsingBranch());
                    loopInput = false;
                    break;
                default:
                    System.out.println(AnsiColors.RED + "Invalid input please select again" + AnsiColors.RESET);

            }


        }

        newCustomer.verify();
        loopInput = true;

        while(loopInput) {
            System.out.println("Enter username and password: ");
            input = inputScanner.nextLine();

//            Validation
            if (input.length() >= 5) {
                newCustomer.setUserName(input);
                newCustomer.setPassword(input);
                loopInput = false;

            } else {
                System.out.println(AnsiColors.RED + "Username and password validation failed" + AnsiColors.RESET);
            }
        }

        loopInput = true;

        while(loopInput) {
            System.out.println("Enter display name: ");
            input = inputScanner.nextLine();

//            Validation
            if (input.length() > 1) {
                newCustomer.setDisplayName(input);
                loopInput = false;

            } else {
                System.out.println(AnsiColors.RED + "display name validation failed" + AnsiColors.RESET);
            }
        }


//        Send Email
        this.notificationService.sendNotification(newCustomer, "Your Serendib account has been created");

        System.out.println("Onboarding is complete !!");
        System.out.println("Ok now login using user name and password");


        return newCustomer;
    }

    @Override
    public Customer login() {
        return null;
    }
}
