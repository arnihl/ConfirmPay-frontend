package is.hi.hbv.conpay.Model;

import java.util.Date;

public class Customer {
    private long id;
    private String cName;
    private Date cDate;
    private int numOfEvents;
    private double rating;
    private String password;
    private String email;

    public Customer() {
    }

    public Customer(String name, Date date, int numOfEvents,
                    double rating, String password, String email) {
        this.cName = name;
        this.cDate = date;
        this.numOfEvents = numOfEvents;
        this.rating = rating;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return cName;
    }

    public void setName(String cName) {
        this.cName = cName;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public int getNumOfEvents() {
        return numOfEvents;
    }

    public void setNumOfEvents(int numOfEvents) {
        this.numOfEvents = numOfEvents;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}