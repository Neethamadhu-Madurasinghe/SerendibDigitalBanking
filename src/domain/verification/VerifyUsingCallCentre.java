package domain.verification;

import application.AnsiColors;
import domain.Customer;

public class VerifyUsingCallCentre implements VerificationStrategy{
    @Override
    public boolean verifyAccount(Customer customer) {
        System.out.println(AnsiColors.CYAN + "Verifying using Call Centre" + AnsiColors.RESET);
        customer.setVerified(true);
        return true;
    }
}
