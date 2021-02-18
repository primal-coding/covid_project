package sample;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Controller {
    ArrayList <Contact> contacts = new ArrayList<Contact>();

    void addContact(Contact contact){
        // check for duplicates first
        contacts.add(contact);
    }

    boolean contactExist(String id){
        for (Contact contact : contacts){
            if (contact.getId() == id)
                return true;
        }
        return false;
    }

    void getList(ObservableList<String> items){
        for (Contact c : contacts){
            items.add(contacts.toString());
        }
    }
}
