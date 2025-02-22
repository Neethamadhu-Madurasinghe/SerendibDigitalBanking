package domain.casa;

import external.CASACustomerData;
import external.CASASystem;

import java.util.List;

public class CASAAdapter implements CASAInterface {

    @Override
    public CustomerData getCustomerDataByAccountNumber(String accountNumber) {
        List<CASACustomerData> customerDataList = CASASystem.getCASACustomerInformation(accountNumber);

        if (customerDataList.size() == 0) throw new RuntimeException("No such user");
        else return new CustomerData(
                customerDataList.getFirst().name(),
                customerDataList.getFirst().phoneNumber(),
                customerDataList.getFirst().emailAddress()
        );
    }
}
