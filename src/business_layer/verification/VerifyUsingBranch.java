package business_layer.verification;

import application_layer.AnsiColors;
import business_layer.Customer;

public class VerifyUsingBranch implements VerificationStrategy{
    @Override
    public boolean verifyAccount(Customer customer) {
        System.out.println(AnsiColors.CYAN + "Verified using Branch" + AnsiColors.RESET);
        customer.setVerified(true);
        return false;
    }
}
