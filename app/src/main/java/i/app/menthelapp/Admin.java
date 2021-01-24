package i.app.menthelapp;

public class Admin {
   String adminFName;
   String adminSName;
   String adminEmail;
   String adminPass;
    String adminId;


    String adminName = adminSName + adminFName;

    public Admin() {
    }

    public Admin(String adminFName, String adminSName, String adminEmail) {
    }

    public Admin(String adminFName, String adminSName, String adminEmail, String adminPass, String admminId) {
        this.adminFName = adminFName;
        this.adminSName = adminSName;
        this.adminEmail = adminEmail;
        this.adminPass = adminPass;
        this.adminId = admminId;
    }


    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminFName() {
        return adminFName;
    }

    public void setAdminFName(String adminFName) {
        this.adminFName = adminFName;
    }

    public String getAdminSName() {
        return adminSName;
    }

    public void setAdminSName(String adminSName) {
        this.adminSName = adminSName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
