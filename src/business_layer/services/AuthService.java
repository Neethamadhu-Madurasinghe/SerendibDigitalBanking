package business_layer.services;

import application_layer.AnsiColors;
import business_layer.Customer;
import business_layer.User;
import business_layer.exceptions.CustomException;
import business_layer.notifications.service.NotificationService;
import business_layer.support.IDGenerator;
import data_layer.casa.CASAInterface;
import data_layer.repository.UserRepository;

import java.util.Optional;
import java.util.Scanner;

public abstract class AuthService {

    protected Scanner inputScanner;
    protected CASAInterface CASASystem;
    protected NotificationService notificationService;
    protected IDGenerator idGenerator;
    protected UserRepository customerRepository;

    public AuthService(
            CASAInterface CASASystem,
            NotificationService notificationService,
            IDGenerator idGenerator,
            UserRepository customerRepository
    ) {
        this.CASASystem = CASASystem;
        this.notificationService = notificationService;
        this.idGenerator = idGenerator;
        this.customerRepository = customerRepository;
        this.inputScanner = new Scanner(System.in);
    }


    abstract User createUser(String type);
    abstract public User onboard();
    abstract Customer selectLanguage(Customer customer);
    abstract Customer takeNIC(Customer customer);
    abstract Customer fetchCASACustomer(Customer customer) throws CustomException;
    abstract Customer verifyUser(Customer customer);
    abstract String takeUsernameAndPassword();
    abstract User handleOTP(User user) throws CustomException;


    public User login() {
        System.out.println(AnsiColors.YELLOW + "=============== Login using 2FA ==============" + AnsiColors.RESET);


        boolean loopInput = true;
        String input = "";
        User currentUser = null;

        while(loopInput) {
            String userName = this.takeUsernameAndPassword();
            String password = userName;

            Optional<User> optionalCustomer = this.customerRepository.getCustomerByUsernameAndPassword(userName, password);

            if (optionalCustomer.isPresent()) {
                currentUser = optionalCustomer.get();
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
}
