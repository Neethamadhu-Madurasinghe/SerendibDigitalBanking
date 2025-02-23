package business_layer.verification;

import application_layer.AnsiColors;
import business_layer.Customer;

public class VerifyUsingCallCentre implements VerificationStrategy{
    @Override
    public boolean verifyAccount(Customer customer) {
        System.out.println(AnsiColors.CYAN + "Verified using Call Centre" + AnsiColors.RESET);
        customer.setVerified(true);
        return true;
    }
}
