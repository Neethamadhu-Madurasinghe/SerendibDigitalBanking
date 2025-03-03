package business_layer.services;

import application_layer.AnsiColors;
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

public class AuthServiceImpl implements AuthService {
    Scanner inputScanner = new Scanner(System.in);
    private CASAInterface CASASystem;
    private NotificationService notificationService;
    private IDGenerator idGenerator;
    private CustomerRepository customerRepository;

    public AuthServiceImpl(
            CASAInterface CASASystem,
            NotificationService notificationService,
            IDGenerator idGenerator,
            CustomerRepository customerRepository
    ) {
        this.CASASystem = CASASystem;
        this.notificationService = notificationService;
        this.idGenerator = idGenerator;
        this.customerRepository = customerRepository;
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
                    newCustomer.setCASANumber(input);
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


        if (!this.handleOTP(newCustomer)) {
            return null;
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

        input = this.takeUsernameAndPassword();
        newCustomer.setUserName(input);
        newCustomer.setPassword(input);

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
        System.out.println();

//        Set customer's otp preference to Authenticator app randomly
        newCustomer.setOtpChannel(OTPChannel.AUTHENTICATOR_APP);

        this.customerRepository.saveCustomer(newCustomer);
        return login();
    }

    @Override
    public Customer login() {
        System.out.println(AnsiColors.YELLOW + "=============== Login using 2FA ==============" + AnsiColors.RESET);


        boolean loopInput = true;
        String input = "";
        Customer currentCustomer = null;

        while(loopInput) {
            String userName = this.takeUsernameAndPassword();
            String password = userName;

            Optional<Customer> optionalCustomer = this.customerRepository.getCustomerByUsernameAndPassword(userName, password);

            if (optionalCustomer.isPresent()) {
                currentCustomer = optionalCustomer.get();
                System.out.println(AnsiColors.GREEN + "User found" + AnsiColors.RESET);
                loopInput = false;
            }
            else {
                System.out.println(AnsiColors.RED + "No User found" + AnsiColors.RESET);
                return null;
            }
        }

        if (!this.handleOTP(currentCustomer)) {
            return null;
        }

        System.out.println(AnsiColors.GREEN + "Login successful" + AnsiColors.RESET);
        System.out.println(AnsiColors.GREEN + "This is your dashboard" + AnsiColors.RESET);
        System.out.println();

        return currentCustomer;
    }

    @Override
    public String takeUsernameAndPassword() {
        String input = "";

        while(true) {
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
    public boolean handleOTP(Customer currentCustomer) {
        String input = "";
        String otp = "1234";
        int otpAttempts = 3;

        while (otpAttempts > 0) {
            System.out.println(AnsiColors.GREEN + "Sending OTP for the " + (3 - otpAttempts + 1) + " time. (" + otp + ")" + AnsiColors.RESET);
            notificationService.sendOtp(currentCustomer, otp);

            while(true) {
                System.out.println("Enter OTP: ");
                System.out.println("Select OPT option\n1. Enter correct OTP\n2. Request new OTP\n3. Enter incorrect OTP\n");
                input = inputScanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println(AnsiColors.GREEN + "OTP is correct" + AnsiColors.RESET);
                        return true;
                    case "2":
                        otpAttempts--;
                        if (otpAttempts >= 1) {
                            System.out.println("Requesting OTP again");
                        } else {
                            System.out.println(AnsiColors.RED + "Login failed: Locking the account" + AnsiColors.RESET);
                            return false;
                        }
                        break;
                    default:
                        System.out.println(AnsiColors.RED + "Invalid OTP please try again" + AnsiColors.RESET);

                }
            }

        }
        return false;
    }


}
