package business_layer.services;

import application_layer.AnsiColors;
import business_layer.exceptions.CustomException;
import data_layer.repository.CustomerRepository;
import business_layer.Customer;
import business_layer.Language;
import business_layer.OTPChannel;
import data_layer.casa.CASAInterface;
import data_layer.casa.CustomerData;
import business_layer.notifications.service.NotificationService;
import business_layer.support.IDGenerator;
import business_layer.verification.VerifyUsingBranch;
import business_layer.verification.VerifyUsingCallCentre;

import java.util.Optional;
import java.util.Scanner;

public class AuthServiceImpl extends AuthService {


    public AuthServiceImpl(CASAInterface CASASystem,
                           NotificationService notificationService,
                           IDGenerator idGenerator,
                           CustomerRepository customerRepository) {
        super(CASASystem, notificationService, idGenerator, customerRepository);
    }

    @Override
    public Customer onboard() {
        Scanner inputScanner = new Scanner(System.in);
        boolean loopInput = true;
        String input = "";
        Customer newCustomer = new Customer(idGenerator.nextId());

        System.out.println(AnsiColors.YELLOW + "=============== Registering customer ==============" + AnsiColors.RESET);

        newCustomer = this.selectLanguage(newCustomer);
        System.out.println("You selected " + newCustomer.getLanguage());

        newCustomer = this.takeNIC(newCustomer);


        try {
            newCustomer = this.fetchCASACustomer(newCustomer);
        } catch (CustomException e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
        }


        try {
            newCustomer = this.handleOTP(newCustomer);
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

        this.customerRepository.saveCustomer(newCustomer);
        return login();
    }


    @Override
    public String takeUsernameAndPassword() {
        String input = "";

        while (true) {
            System.out.println("Enter username and password: ");
            input = inputScanner.nextLine();
            String userName = input;

//            Validation
            if (input.length() >= 5) {
                return userName;

            } else {
                System.out.println(AnsiColors.RED + "Username and password validation failed" + AnsiColors.RESET);
            }
        }
    }

    @Override
    public Customer handleOTP(Customer currentCustomer) {
        String input = "";
        String otp = "1234";
        int otpAttempts = 3;

        while (otpAttempts > 0) {
            System.out.println(AnsiColors.GREEN + "Sending OTP for the " + (3 - otpAttempts + 1) + " time. (" + otp + ")" + AnsiColors.RESET);
            notificationService.sendOtp(currentCustomer, otp);

            while (true) {
                System.out.println("Enter OTP: ");
                System.out.println("Select OPT option\n1. Enter correct OTP\n2. Request new OTP\n3. Enter incorrect OTP\n");
                input = inputScanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println(AnsiColors.GREEN + "OTP is correct" + AnsiColors.RESET);
                        currentCustomer.setOtp(1234);
                        return currentCustomer;
                    case "2":
                        otpAttempts--;
                        if (otpAttempts >= 1) {
                            System.out.println("Requesting OTP again");
                        } else {
                            throw new CustomException("Login failed: Locking the account");
                        }
                        break;
                    default:
                        System.out.println(AnsiColors.RED + "Invalid OTP please try again" + AnsiColors.RESET);

                }
            }

        }
        throw new CustomException("OTP failed");
    }

    @Override
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
    public Customer selectLanguage(Customer customer) {
        String input = "";
        boolean loopInput = true;

        while (loopInput) {
            System.out.println("Select your language\n1. English\n2. Sinhala\n");
            input = inputScanner.nextLine();

            switch (input) {
                case "1":
                    customer.setLanguage(Language.ENGLISH);
                    loopInput = false;
                    break;
                case "2":
                    customer.setLanguage(Language.SINHALA);
                    loopInput = false;
                    break;
                default:
                    System.out.println(AnsiColors.RED + "Invalid input please select again" + AnsiColors.RESET);

            }
        }

        return customer;
    }

    @Override
    public Customer takeNIC(Customer customer) {
        String input = "";

        while (true) {
            System.out.println("Enter your NIC/Passport Number: ");
            input = inputScanner.nextLine();

            if (input.length() >= 5) {
                customer.setNicPassportNumber(input);
                return customer;

            } else {
                System.out.println(AnsiColors.RED + "NIC/Passport validation failed" + AnsiColors.RESET);
            }
        }
    }

    @Override
    public Customer fetchCASACustomer(Customer customer) throws CustomException {
        String input = "";
        boolean loopInput = true;

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
