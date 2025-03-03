package data_layer.repository;

import business_layer.User;

import java.util.Optional;

public interface UserRepository {
    User saveUser(User customer);
    Optional<User> getCustomerByUsernameAndPassword(String username, String password);
}
