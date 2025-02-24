package data_layer.repository;

import business_layer.Customer;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository {
    private List<Customer> savedCustomers = new LinkedList<>();
    private static CustomerRepository instance = null;

//    Assuming this has all the database connecting logic, we can add singleton pattern to this as well
    public static CustomerRepository getInstance() {
        if (instance != null) {
            synchronized (CustomerRepositoryImpl.class) {
                if (instance != null) {
                    instance = new CustomerRepositoryImpl();
                }
            }
        }

        return instance;
    }


    private CustomerRepositoryImpl() {}

    @Override
    public Customer saveCustomer(Customer customer) {
        this.savedCustomers.add(customer);
        return customer;
    }

    @Override
    public Optional<Customer> getCustomerByUsernameAndPassword(String username, String password) {
        List<Customer> foundList = this.savedCustomers.stream().filter(customer -> {
            return customer.getUserName().equals(username);
        }).toList();

        if (foundList.size() > 0) {
            return Optional.of(foundList.getFirst());
        } else {
            return Optional.empty();
        }
    }
}
