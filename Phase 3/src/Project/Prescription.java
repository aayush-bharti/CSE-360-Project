package Project;

import java.util.Objects;

public class Prescription {
    private String dateTime;
    private String prescriptionBody;
    private String phoneNumber;
    private String email;
    private String insuranceProvider;
    private String preferredPharmacy;
    
    public Prescription(String dateTime, String prescriptionBody, String phoneNumber, String email, String insuranceProvider, String preferredPharmacy) {
        this.dateTime = dateTime;
        this.prescriptionBody = prescriptionBody;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.insuranceProvider = insuranceProvider;
        this.preferredPharmacy = preferredPharmacy;
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(prescriptionBody, that.prescriptionBody) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(insuranceProvider, that.insuranceProvider) &&
                Objects.equals(preferredPharmacy, that.preferredPharmacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, prescriptionBody, phoneNumber, email, insuranceProvider, preferredPharmacy);
    }
    
    public String getDateTime() {
        return dateTime;
    }

    public String getPrescriptionBody() {
        return prescriptionBody;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getInsuranceProvider() {
        return insuranceProvider;
    }
    
    public String getPreferredPharmacy() {
        return preferredPharmacy;
    }
   
}
