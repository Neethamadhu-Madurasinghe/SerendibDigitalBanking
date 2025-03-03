package business_layer.services;

import application_layer.AnsiColors;
import business_layer.Customer;
import business_layer.exceptions.CustomException;
import business_layer.notifications.service.NotificationService;
import business_layer.support.IDGenerator;
import data_layer.casa.CASAInterface;
import data_layer.repository.CustomerRepository;

import java.util.Optional;
import java.util.Scanner;

public abstract class AuthService {

    protected Scanner inputScanner;
    protected CASAInterface CASASystem;
    protected NotificationService notificationService;
    protected IDGenerator idGenerator;
    protected CustomerRepository customerRepository;

    public AuthService(
            CASAInterface CASASystem,
            NotificationService notificationService,
            IDGenerator idGenerator,
            CustomerRepository customerRepository
    ) {
        this.CASASystem = CASASystem;
        this.notificationService = notificationService;
        this.idGenerator = idGenerator;
        this.customerRepository = customerRepository;
        this.inputScanner = new Scanner(System.in);
    }


    abstract public Customer onboard();
    abstract Customer selectLanguage(Customer customer);
    abstract Customer takeNIC(Customer customer);
    abstract Customer fetchCASACustomer(Customer customer) throws CustomException;
    abstract Customer verifyUser(Customer customer);
    abstract String takeUsernameAndPassword();
    abstract Customer handleOTP(Customer customer) throws CustomException;


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

        try {
            currentCustomer = this.handleOTP(currentCustomer);
        } catch (CustomException e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
            return null;
        }


        System.out.println(AnsiColors.GREEN + "Login successful" + AnsiColors.RESET);
        System.out.println(AnsiColors.GREEN + "This is your dashboard" + AnsiColors.RESET);
        System.out.println();

        return currentCustomer;
    }
}
