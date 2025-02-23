package data_layer.repository;

import business_layer.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Customer saveCustomer(Customer customer);
    Optional<Customer> getCustomerByUsernameAndPassword(String username, String password);
}
