package domain;

import domain.support.IDGenerator;

public class CustomerBuilderImpl implements CustomerBuilder{

    private Customer customer = null;
    private IDGenerator idGenerator;

    public CustomerBuilderImpl(IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
        this.customer = new Customer(this.idGenerator.nextId());
    }

    @Override
    public CustomerBuilder language(Language language) {
        this.customer.setLanguage(language);
        return this;
    }

    @Override
    public CustomerBuilder name(String name) {
        this.customer.setName(name);
        return this;
    }

    @Override
    public CustomerBuilder nicPassport(String number) {
        this.customer.setNicPassportNumber(number);
        return this;
    }

    @Override
    public CustomerBuilder otp(int otp) {
        this.customer.setOtp(otp);
        return this;
    }

    @Override
    public CustomerBuilder CASANumber(String CASANumber) {
        this.customer.setCASANumber(CASANumber);
        return this;
    }

    @Override
    public CustomerBuilder email(String email) {
        this.customer.setEmail(email);
        return this;
    }

    @Override
    public CustomerBuilder mobileNumber(String mobileNumber) {
        this.customer.setMobileNumber(mobileNumber);
        return this;
    }

    @Override
    public CustomerBuilder userName(String userName) {
        this.customer.setUserName(userName);
        return this;
    }

    @Override
    public CustomerBuilder password(String password) {
        this.customer.setPassword(password);
        return this;
    }

    @Override
    public CustomerBuilder displayName(String displayName) {
        this.customer.setDisplayName(displayName);
        return this;
    }

    @Override
    public CustomerBuilder otpChanel(OTPChannel otpChannel) {
        this.customer.setOtpChannel(otpChannel);
        return this;
    }

    @Override
    public Customer getCustomer() {
        Customer tempCustomer = this.customer;
        this.customer = new Customer(this.idGenerator.nextId());
        return tempCustomer;
    }
}
