package business_layer.services;

import business_layer.Customer;

public interface AuthService {
    public Customer onboard();

    public Customer login();

    public String takeUsernameAndPassword();
    public boolean handleOTP(Customer customer);
}
