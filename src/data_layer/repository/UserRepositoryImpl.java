package data_layer.repository;

import business_layer.Customer;
import business_layer.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private List<User> savedUsers = new LinkedList<>();
    private static UserRepository instance = null;

//    Assuming this has all the database connecting logic, we can add singleton pattern to this as well
    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepositoryImpl.class) {
                if (instance == null) {
                    instance = new UserRepositoryImpl();
                }
            }
        }

        return instance;
    }


    private UserRepositoryImpl() {}

    @Override
    public User saveUser(User user) {
        this.savedUsers.add(user);
        return user;
    }

    @Override
    public Optional<User> getCustomerByUsernameAndPassword(String username, String password) {
        List<User> foundList = this.savedUsers.stream().filter(user -> {
            return user.getUserName().equals(username);
        }).toList();

        if (foundList.size() > 0) {
            return Optional.of(foundList.getFirst());
        } else {
            return Optional.empty();
        }
    }
}
