package sample;

import javafx.collections.ObservableList;
import java.util.ArrayList;

public class Controller {

    private ArrayList <Contact> contacts = new ArrayList<Contact>();
    private Contact contact = new Contact();

    public Controller(){

    }

    // private for separation of concerns
    private boolean contactExist(String id){
        if (contacts.isEmpty()){
            return false;   // if the ArrayList is empty no contact in it
        }
        for (Contact c : contacts){
            if (id.equals(c.getId())) {
                return true;    // contact found in ArrayList
            }
        }
        return false;   // contact is not in the ArrayList
    }

    boolean addContact(Contact contact){
        // deep copy, to prevent shallow copy(with all contacts pointing to the same reference in the ArrayList)
        Contact c = new Contact(contact.getFirstName(), contact.getLastName(), contact.getId(), contact.getPhoneNumber());
        if (contactExist(c.getId()))
            return false;   // contact exist => it CANNOT be added (false)
        contacts.add(c);    // true => contact added
        return true;
    }

    boolean removeContact(Contact contact){
        if (contactExist(contact.getId())){

            return true;   // if the ArrayList is empty no contact to remove
        }
        return false;   // contact is not in the ArrayList
    }

    void getList(ObservableList<String> listOfContacts){
        for (Contact c : contacts){             // through shallow copy the list gets updated
            listOfContacts.add(c.toString());   // (for the ListView, though the MainView list listener)
        }

    }

    boolean loadList(){
        Boolean success;
        SaveNLoader load = new SaveNLoader();
        success = load.load(contacts);
        return success;
    }

    boolean saveList(){
        Boolean success;
        SaveNLoader save = new SaveNLoader();
        success = save.save(contacts);
        return success;
    }
}
