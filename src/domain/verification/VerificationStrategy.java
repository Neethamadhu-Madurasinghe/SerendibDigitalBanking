package domain.verification;

import domain.Customer;

public interface VerificationStrategy {
    boolean verifyAccount(Customer customer);
}
