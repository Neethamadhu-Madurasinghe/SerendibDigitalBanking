package business_layer.services;

import application_layer.AnsiColors;
import business_layer.User;
import business_layer.exceptions.CustomException;
import data_layer.repository.UserRepository;
import business_layer.Customer;
import business_layer.OTPChannel;
import data_layer.casa.CASAInterface;
import data_layer.casa.CustomerData;
import business_layer.notifications.service.NotificationService;
import business_layer.support.IDGenerator;
import business_layer.verification.VerifyUsingBranch;
import business_layer.verification.VerifyUsingCallCentre;

import java.util.Locale;
import java.util.Scanner;

public class AuthServiceImpl extends AuthService {


    public AuthServiceImpl(CASAInterface CASASystem,
                           NotificationService notificationService,
                           IDGenerator idGenerator,
                           UserRepository customerRepository) {
        super(CASASystem, notificationService, idGenerator, customerRepository);
    }

    @Override
    public User createUser(String type) {
        if (type.toUpperCase(Locale.ROOT).equals("CUSTOMER")) return new Customer(this.idGenerator.nextId());
        else throw new CustomException("No such user");
    }

    @Override
    public User onboard() {
        Scanner inputScanner = new Scanner(System.in);
        boolean loopInput = true;
        String input = "";
        Customer newCustomer = (Customer) this.createUser("CUSTOMER");

        System.out.println(AnsiColors.YELLOW + "=============== Registering customer ==============" + AnsiColors.RESET);

        newCustomer = (Customer) this.selectLanguage(newCustomer);
        System.out.println("You selected " + newCustomer.getLanguage());

        newCustomer = (Customer) this.takeNIC(newCustomer);


        try {
            newCustomer = (Customer) this.getCASAUserDetails(newCustomer);
        } catch (CustomException e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
        }


        try {
            newCustomer = (Customer) this.handleOTP(newCustomer);
        } catch (CustomException e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
            return null;
        }

        newCustomer = this.verifyUser(newCustomer);


        input = this.takeUsernameAndPassword();
        newCustomer.setUserName(input);
        newCustomer.setPassword(input);

        while (loopInput) {
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
        System.out.println();

//        Set customer's otp preference to Authenticator app randomly - we can take it as an input if needed
        newCustomer.setOtpChannel(OTPChannel.AUTHENTICATOR_APP);

        this.userRepository.saveUser(newCustomer);
        return login();
    }



    public Customer verifyUser(Customer customer) {
        String input = "";
        boolean loopInput = true;

        while (loopInput) {
            System.out.println("Select account verification method\n1. Contact Call Centre\n2. At a Serendib Branch\n");
            input = inputScanner.nextLine();

            switch (input) {
                case "1":
                    customer.setVerificationStrategy(new VerifyUsingCallCentre());
                    loopInput = false;
                    break;
                case "2":
                    customer.setVerificationStrategy(new VerifyUsingBranch());
                    loopInput = false;
                    break;
                default:
                    System.out.println(AnsiColors.RED + "Invalid input please select again" + AnsiColors.RESET);
            }

        }

        customer.verify();
        return customer;
    }



    @Override
    public User getCASAUserDetails(User user) throws CustomException {
        String input = "";
        boolean loopInput = true;
        Customer customer = (Customer) user;

        while (loopInput) {
            System.out.println("Enter your CASA Acc. Number: ");
            input = inputScanner.nextLine();

//            Validation
            if (input.length() >= 5) {
                try {
                    CustomerData data = CASASystem.getCustomerDataByAccountNumber(input);
                    customer.setCASANumber(input);
                    customer.setName(data.name());
                    customer.setMobileNumber(data.phoneNumber());
                    customer.setEmail(data.email());
                    loopInput = false;
                    return customer;

                } catch (CustomException e) {
                    System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
                }

            } else {
                System.out.println(AnsiColors.RED + "CASA Acc no. validation failed" + AnsiColors.RESET);
            }
        }

        throw new CustomException("Could not fetch CASA customer details");
    }


}
