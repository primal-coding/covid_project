package sample;

import java.io.*;
import java.util.ArrayList;

public class SaveNLoader {
        ArrayList<Contact> contacts =new ArrayList<Contact>();
//    public SaveNLoader (){
//        // create the file to load and save for the first time
//        File file = new File("listOfContacts.loc");
//        try {
//            if(file.createNewFile()){
//                FileOutputStream f = new FileOutputStream(new File("listOfContacts.loc"));
//                ObjectOutputStream ooStream = new ObjectOutputStream(f);
//
//                // does not work: (write(int) then writeOject() is to no avail)
//                // ooStream.write(0);     // for the 'load' method
//                ooStream.close();
//                f.close();
//            }
//        }
//        catch(IOException e){
//            System.out.println("constructor exception");
//        }
//    }

    boolean save(ArrayList<Contact> contacts) {
        boolean success = true;
        try{
            FileOutputStream file = new FileOutputStream(new File("listOfContacts.loc"));
            ObjectOutputStream ooStream = new ObjectOutputStream(file);

            // for (Contact c : contacts){
                ooStream.writeObject(contacts);
            // }
            System.out.println("save contacts ok " + contacts.toString());
            ooStream.close();
            file.close();
        }
        catch (IOException e){    // for test

            e.printStackTrace();
            System.out.println("save IOexception");
            success = false;
        }
        catch (Exception e){
            e.printStackTrace();   // for test
            System.out.println("save exception");
            success = false;
        }
        return success;
    }

    boolean load(){
        boolean success = true;
        try{
            FileInputStream file = new FileInputStream(new File("listOfContacts.loc"));
            ObjectInputStream oiStream = new ObjectInputStream(file);

            //for (int i = 0; i < sizeOfList; i++)
            //    contacts.add((Contact) oiStream.readObject());
            contacts = (ArrayList<Contact>) oiStream.readObject();
            System.out.println("load contacts ok " + contacts.toString());
            oiStream.close();
            file.close();

        }
        catch (EOFException eof){
            System.out.println("load eof exception");
        }
        catch (Exception e){
//            e.printStackTrace();      // for testing purposes
//            System.out.println("load exception");
            success = false;
        }
        return success;
    }

    ArrayList<Contact> getList(){
        return this.contacts;
    }
}
