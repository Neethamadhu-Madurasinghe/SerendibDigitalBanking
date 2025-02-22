package domain.services;

import domain.Customer;

public interface AuthService {
    public Customer onboard();

    public Customer login();
}
