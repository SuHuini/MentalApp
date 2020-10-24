package i.app.menthelapp;

public class Client {

    String clientFName;
    String clientSName;
    String clientUName;
    String clientEmail;
    //int clientPhone;
    String clientPassword;

    public Client(){

    }

    public Client( String clientFName, String clientSName, String clientUName, String clientEmail,  String clientPassword) {
        this.clientFName = clientFName;
        this.clientSName = clientSName;
        this.clientUName = clientUName;
        this.clientEmail = clientEmail;
       // this.clientPhone = clientPhone;
        this.clientPassword = clientPassword;
    }

    public String getClientFName() {
        return clientFName;
    }

    public void setClientFName(String clientFName) {
        this.clientFName = clientFName;
    }

    public String getClientSName() {
        return clientSName;
    }

    public void setClientSName(String clientSName) {
        this.clientSName = clientSName;
    }

    public String getClientUName() {
        return clientUName;
    }

    public void setClientUName(String clientUName) {
        this.clientUName = clientUName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    //public int getClientPhone() {
   //     return clientPhone;
   // }

   // public void setClientPhone(int clientPhone) {
//        this.clientPhone = clientPhone;
//    }
//
    public String getClientPassword() { return clientPassword;    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }



}
