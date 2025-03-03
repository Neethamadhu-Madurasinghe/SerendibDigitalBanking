package business_layer.services;

import business_layer.User;
import business_layer.exceptions.CustomException;

public interface IAuthService {

    User onboard();
    User login();
    User selectLanguage(User user);
    User takeNIC(User user);
    User getCASAUserDetails(User user) throws CustomException;
    String takeUsernameAndPassword();
    User handleOTP(User user) throws CustomException;

}
