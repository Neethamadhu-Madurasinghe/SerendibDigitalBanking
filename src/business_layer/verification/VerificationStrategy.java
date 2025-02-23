package business_layer.verification;

import business_layer.Customer;

public interface VerificationStrategy {
    boolean verifyAccount(Customer customer);
}
