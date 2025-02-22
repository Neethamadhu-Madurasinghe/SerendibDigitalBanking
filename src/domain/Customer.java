package domain;

import domain.verification.VerificationStrategy;

public class Customer {
    private int id;
    private Language language;
    private String nicPassportNumber;
    private String name;
    private int otp;
    private String CASANumber;
    private String email;
    private String mobileNumber;
    private String password;
    private String userName;
    private String displayName;
    private boolean isVerified;
    private OTPChannel otpChannel;

    private VerificationStrategy verificationStrategy;


    public Customer(int id) {
        this.id = id;
    }

    public String getNicPassportNumber() {
        return nicPassportNumber;
    }

    public void setNicPassportNumber(String nicPassportNumber) {
        this.nicPassportNumber = nicPassportNumber;
    }

    public int getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getCASANumber() {
        return CASANumber;
    }

    public void setCASANumber(String CASANumber) {
        this.CASANumber = CASANumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public OTPChannel getOtpChannel() {
        return otpChannel;
    }

    public void setOtpChannel(OTPChannel otpChannel) {
        this.otpChannel = otpChannel;
    }

    public void setVerificationStrategy(VerificationStrategy verificationStrategy) {
        this.verificationStrategy = verificationStrategy;
    }

    public void verify() {
        verificationStrategy.verifyAccount(this);
    }
}
