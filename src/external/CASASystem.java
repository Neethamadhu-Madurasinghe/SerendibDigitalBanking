package external;

import java.util.Collections;
import java.util.List;

public class CASASystem {
    public static List<CASACustomerData> getCASACustomerInformation(String accountNumber) {
        if (accountNumber.equals("123456")) return Collections.emptyList();
        else if ((accountNumber.equals("234567"))) return Collections.singletonList(
                new CASACustomerData("John doe",  "+9412345", "john@abc.com", "Some data"));
        else return Collections.singletonList(
                new CASACustomerData("No email user",  "+9498712", null, "Some data"));
    }
}
