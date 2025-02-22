package external;

import java.util.Collections;
import java.util.List;

public class CASASystem {
    public static List<CASACustomerData> getCASACustomerInformation(String accountNumber) {
        if (accountNumber == "123456") return Collections.emptyList();
        else return Collections.singletonList(new CASACustomerData("John doe", "Something..."));
    }
}
