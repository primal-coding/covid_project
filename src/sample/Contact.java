package sample;

import java.io.Serializable;

public class Contact implements Serializable{

    private String firstName;
    private String lastName;
    private String id;
    private String phoneNumber;

    public Contact() {
        this.firstName = "";
        this.lastName = "";
        this.id = "";
        this.phoneNumber = "";
    }

    public Contact(String firstName, String lastName, String id, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", id='" + id + "', phoneNumber='" + phoneNumber + "'.";
    }
}
