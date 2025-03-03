package business_layer.services;

import business_layer.User;
import business_layer.exceptions.CustomException;

public interface IAuthService {

    abstract public User onboard();
    abstract User selectLanguage(User user);
    abstract User takeNIC(User user);
    abstract User getCASAUserDetails(User user) throws CustomException;
    abstract String takeUsernameAndPassword();
    abstract User handleOTP(User user) throws CustomException;
}
