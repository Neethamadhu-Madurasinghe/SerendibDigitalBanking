package business_layer.services;

import application_layer.AnsiColors;
import business_layer.Language;
import business_layer.User;
import business_layer.exceptions.CustomException;
import business_layer.notifications.service.NotificationService;
import business_layer.support.IDGenerator;
import data_layer.casa.CASAInterface;
import data_layer.repository.UserRepository;

import java.util.Optional;
import java.util.Scanner;

public abstract class AuthService implements IAuthService {

    protected Scanner inputScanner;
    protected CASAInterface CASASystem;
    protected NotificationService notificationService;
    protected IDGenerator idGenerator;
    protected UserRepository userRepository;

    public AuthService(
            CASAInterface CASASystem,
            NotificationService notificationService,
            IDGenerator idGenerator,
            UserRepository userRepository
    ) {
        this.CASASystem = CASASystem;
        this.notificationService = notificationService;
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
        this.inputScanner = new Scanner(System.in);
    }


    abstract User createUser(String type);

    public User login() {
        System.out.println(AnsiColors.YELLOW + "=============== Login using 2FA ==============" + AnsiColors.RESET);


        boolean loopInput = true;
        String input = "";
        User currentUser = null;

        while(loopInput) {
            String userName = this.takeUsernameAndPassword();
            String password = userName;

            Optional<User> optionalUser = this.userRepository.getCustomerByUsernameAndPassword(userName, password);

            if (optionalUser.isPresent()) {
                currentUser = optionalUser.get();
                System.out.println(AnsiColors.GREEN + "User found" + AnsiColors.RESET);
                loopInput = false;
            }
            else {
                System.out.println(AnsiColors.RED + "No User found" + AnsiColors.RESET);
                return null;
            }
        }

        try {
            currentUser = this.handleOTP(currentUser);
        } catch (CustomException e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
            return null;
        }


        System.out.println(AnsiColors.GREEN + "Login successful" + AnsiColors.RESET);
        System.out.println(AnsiColors.GREEN + "This is your dashboard" + AnsiColors.RESET);
        System.out.println();

        return currentUser;
    }

    @Override
    public User selectLanguage(User user) {
        String input = "";
        boolean loopInput = true;

        while (loopInput) {
            System.out.println("Select your language\n1. English\n2. Sinhala\n");
            input = inputScanner.nextLine();

            switch (input) {
                case "1":
                    user.setLanguage(Language.ENGLISH);
                    loopInput = false;
                    break;
                case "2":
                    user.setLanguage(Language.SINHALA);
                    loopInput = false;
                    break;
                default:
                    System.out.println(AnsiColors.RED + "Invalid input please select again" + AnsiColors.RESET);

            }
        }

        return user;
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
    public User handleOTP(User user) {
        String input = "";
        String otp = "1234";
        int otpAttempts = 3;

        while (otpAttempts > 0) {
            System.out.println(AnsiColors.GREEN + "Sending OTP for the " + (3 - otpAttempts + 1) + " time. (" + otp + ")" + AnsiColors.RESET);
            notificationService.sendOtp(user, otp);
            boolean breakOut = false;

            while (true && !breakOut) {
                breakOut = false;
                System.out.println("Enter OTP: ");
                System.out.println("Select OPT option\n1. Enter correct OTP\n2. Request new OTP\n3. Enter incorrect OTP\n");
                input = inputScanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println(AnsiColors.GREEN + "OTP is correct" + AnsiColors.RESET);
                        user.setOtp(1234);
                        return user;
                    case "2":
                        otpAttempts--;
                        if (otpAttempts >= 1) {
                            System.out.println("Requesting OTP again");
                        } else {
                            throw new CustomException("Login failed: Locking the account");
                        }
                        breakOut = true;
                        break;
                    default:
                        System.out.println(AnsiColors.RED + "Invalid OTP please try again" + AnsiColors.RESET);

                }
            }

        }
        throw new CustomException("OTP failed");
    }

    @Override
    public User takeNIC(User user) {
        String input = "";

        while (true) {
            System.out.println("Enter your NIC/Passport Number: ");
            input = inputScanner.nextLine();

            if (input.length() >= 5) {
                user.setNicPassportNumber(input);
                return user;

            } else {
                System.out.println(AnsiColors.RED + "NIC/Passport validation failed" + AnsiColors.RESET);
            }
        }
    }

}
