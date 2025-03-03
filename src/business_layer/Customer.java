package business_layer;

import business_layer.verification.VerificationStrategy;

public class Customer extends User {

    private String CASANumber;
    private String displayName;
    private boolean isVerified;

    private VerificationStrategy verificationStrategy;


    public Customer(int id) {
        super(id);
    }

    public String getCASANumber() {
        return CASANumber;
    }

    public void setCASANumber(String CASANumber) {
        this.CASANumber = CASANumber;
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


    public void setVerificationStrategy(VerificationStrategy verificationStrategy) {
        this.verificationStrategy = verificationStrategy;
    }

    public void verify() {
        verificationStrategy.verifyAccount(this);
    }
}
