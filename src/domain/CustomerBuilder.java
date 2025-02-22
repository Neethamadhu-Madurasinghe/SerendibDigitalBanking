package domain;

public interface CustomerBuilder {
    CustomerBuilder language(Language language);
    CustomerBuilder name(String name);
    CustomerBuilder nicPassport(String number);
    CustomerBuilder otp(int otp);
    CustomerBuilder CASANumber(String CASANumber);
    CustomerBuilder email(String email);
    CustomerBuilder mobileNumber(String mobileNumber);
    CustomerBuilder userName(String userName);
    CustomerBuilder password(String password);
    CustomerBuilder displayName(String displayName);
    CustomerBuilder otpChanel(OTPChannel otpChannel);
    Customer getCustomer();
}
