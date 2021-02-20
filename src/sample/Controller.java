package sample;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Controller {
    private ArrayList <Contact> contacts = new ArrayList<Contact>();

    boolean addContact(Contact contact){
        Contact c = new Contact(contact.getFirstName(), contact.getLastName(), contact.getId(), contact.getPhoneNumber());
        if (contactExist(c.getId()))
            return false;   // contact exist => NOT added (false)
        contacts.add(c);
        return true;        // true => contact added
    }

    private boolean contactExist(String id){
        if (contacts.isEmpty()){
            return false;
        }
        for (Contact c : contacts){
            System.out.println("-> " + c.toString() + "<- id: " + id);
            if (id.equals(c.getId())) {
                // System.out.println("<- " + c.toString());
                return true;
            }
        }
        return false;
    }

    void getList(ObservableList<String> items){
        for (Contact c : contacts){
            items.add(c.toString());
            System.out.println("GL-> " + c.toString());
        }

    }
}
