package domain.casa;

import external.CASACustomerData;
import external.CASASystem;

import java.util.List;

public class CASAAdapter implements CASAInterface {

    @Override
    public String getCustomerNameByAccountNumber(String accountNumber) {
        List<CASACustomerData> customerData = CASASystem.getCASACustomerInformation(accountNumber);

        if (customerData.size() == 0) throw new RuntimeException("No such user");
        else return customerData.getFirst().name();
    }
}
