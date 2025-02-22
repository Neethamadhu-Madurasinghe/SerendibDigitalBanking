package domain.services;

import domain.Customer;

public interface Service {
    public Customer onboard();

    public Customer login();
}
