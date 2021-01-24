package i.app.menthelapp;

import java.util.Date;

public class Session {
    String id;
    String clientName;
    String counName;
    String date;
    String clientEmail;
    String counEmail;

    public Session() {
    }

    public Session(String clientName, String counName, String date) {
    }
    public Session(String id, String clientName, String counName, String date, String clientEmail, String counEmail) {
        this.id = id;
        this.clientName = clientName;
        this.counName = counName;
        this.date = date;
        this.clientEmail = clientEmail;
        this.counEmail = counEmail;
    }
    public Session(String id, String clientName, String counName, Date date) {

    }


    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getCounEmail() {
        return counEmail;
    }

    public void setCounEmail(String counEmail) {
        this.counEmail = counEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCounName() {
        return counName;
    }

    public void setCounName(String counName) {
        this.counName = counName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
