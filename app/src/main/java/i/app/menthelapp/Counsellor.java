package i.app.menthelapp;

public class Counsellor {

    String counFName;
    String counSName;
    String counEmail;
    String counPassword;
    String licenseNo;
    String specialization;

    public Counsellor(){

    }
    public Counsellor( String counFName, String counSName, String counEmail,  String counPassword, String licenseNo,  String specialization) {
        this.counFName = counFName;
        this.counSName = counSName;
        this.counEmail = counEmail;
        this.counPassword = counPassword;
        this.licenseNo = licenseNo;
        this.specialization = specialization;
    }
    public Counsellor( String counFName, String counSName,  String licenseNo,  String specialization) {

    }

    public String getCounFName() {
        return counFName;
    }

    public void setCounFName(String counFName) {
        this.counFName = counFName;
    }

    public String getCounSName() {
        return counSName;
    }

    public void setCounSName(String counSName) {
        this.counSName = counSName;
    }

    public String getCounEmail() {
        return counEmail;
    }

    public void setCounEmail(String counEmail) {
        this.counEmail = counEmail;
    }

    public String getCounPassword() {
        return counPassword;
    }

    public void setCounPassword(String counPassword) {
        this.counPassword = counPassword;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
