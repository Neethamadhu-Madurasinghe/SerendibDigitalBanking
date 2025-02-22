package domain.verification;

import application.AnsiColors;
import domain.Customer;

public class VerifyUsingBranch implements VerificationStrategy{
    @Override
    public boolean verifyAccount(Customer customer) {
        System.out.println(AnsiColors.CYAN + "Verified using Branch" + AnsiColors.RESET);
        customer.setVerified(true);
        return false;
    }
}
