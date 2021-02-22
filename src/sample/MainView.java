package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainView {

    // TextFields are used in global class to make it simpler and clearer in the setting up of the scene
    TextField firstNameTextField = new TextField(" ");
    TextField lastNameTextField = new TextField(" ");
    TextField idTextField = new TextField(" ");
    TextField phoneNumberTextField = new TextField(" ");
    Boolean isListValid = false;
    Boolean lastEntrySaved = true;  // as a contact been added but not saved?

    public Scene initScene(Main main, Controller controller){
        // list is the list of the elements for the ListView, as such it must be initialised before
        // before being passed on for the creation of the add/remove/list buttons
        ObservableList<String> list = FXCollections.observableArrayList ("People", "in", "the", "Application");

        // setting the different parts of the scene for the stage
        // the scene will be a BorderPane
        // top: a VBox with 3 rows: stackPane/flowPane(4 HBox)/HBox(add/remove/list buttons)
        // center: ListView
        // bottom: 2 HBox

        // 1st row: StackPane (with only a Label) ... of the scene/VBox
        Label sceneTitle = new Label("<--------------Contacts-------------->");
        StackPane firstRow = new StackPane();
        firstRow.getChildren().add(sceneTitle);

        // 2nd row: FlowPane (for the 4 entries, and automatic rearrangement in case the window gets resized)
        FlowPane secondRow = new FlowPane();
        secondRow = setupTextEntries();         // calling a method for clarity of reading/conciseness

        // 3rd row: HBox (for the add/remove/list buttons)
        HBox thirdRow = new HBox();
        thirdRow = setupPrimaryButtons(list, controller);

        // row 1,2 and 3 in VBox - top of BorderPane (mainPane in this method - NOT fifthRow!!)
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(firstRow,secondRow,thirdRow);

        // 4th row: ListView - center of BorderPane
        ListView<String> fourthRow = new ListView<String>();
        // the 'list'(ObservableList) listener is set after 'fourthRow'(ListView) object is created
        // which updates automatically the ListView when the Observable changes
        list.addListener( (ListChangeListener<String>) change -> fourthRow.setItems(list));
        fourthRow.setItems(list);   // ListView needs a first initialisation for the ObservableList to use it

        // 5th row: borderPane with 2 HBox (with file I/O and Exit buttons)
        BorderPane fifthRow = setupFileExit(list, controller);

        // BorderPane for the scene (5 rows in 3 parts)
        BorderPane.setAlignment(topVBox,Pos.TOP_CENTER);
        BorderPane.setAlignment(fourthRow,Pos.CENTER);
        BorderPane.setAlignment(fifthRow,Pos.BOTTOM_CENTER);

        BorderPane mainPane = new BorderPane();
        mainPane.setTop(topVBox);
        mainPane.setCenter(fourthRow);
        mainPane.setBottom(fifthRow);
        Scene root = new Scene(mainPane, 550, 550);
        return root;
    }

    // secondRow with setup of the 4 labels and textfields
    // 4 HBox in a FlowPane
    private FlowPane setupTextEntries() {
        Label firstNameLabel = new Label("Enter First Name");
        HBox row1 = new HBox();
        row1.setSpacing(30);
        row1.setPadding(new Insets(10, 10, 10, 010));
        row1.getChildren().addAll(firstNameLabel, firstNameTextField);

        Label lastNameLabel = new Label("Enter Last Name");
        HBox row2 = new HBox();
        row2.setSpacing(30);
        row2.setPadding(new Insets(10, 10, 10, 10));
        row2.getChildren().addAll(lastNameLabel, lastNameTextField);

        Label idLabel = new Label("Enter Unique ID");
        HBox row3 = new HBox();
        row3.setSpacing(37);
        row3.setPadding(new Insets(10, 10, 10, 10));
        row3.getChildren().addAll(idLabel, idTextField);

        Label phoneNumberLabel = new Label("Enter Phone Number");
        HBox row4 = new HBox();
        row4.setSpacing(5);
        row4.setPadding(new Insets(10, 10, 10, 10));
        row4.getChildren().addAll(phoneNumberLabel, phoneNumberTextField);

        FlowPane secondRow = new FlowPane();
        secondRow.getChildren().addAll(row1, row2, row3, row4);

        return secondRow;
    }

    // thirdRow: add/remove/list buttons
    private HBox setupPrimaryButtons(ObservableList<String> list, Controller controller){
        Button addButton = new Button("ADD");
        Button removeButton = new Button("Remove");
        Button listButton = new Button("List");
        Contact contact = new Contact();

        // listener to add a contact, if it exists(checked in the controller Class for separation
        // of concerns) with confirmation shown in ListView, display of error otherwise,and
        // reset of the TextFields
        addButton.setOnAction(e -> {
            contact.setFirstName(firstNameTextField.getText());
            contact.setLastName(lastNameTextField.getText());
            contact.setId(idTextField.getText());
            contact.setPhoneNumber(phoneNumberTextField.getText());
            if (controller.addContact(contact)){    // contact has been added
                lastEntrySaved = false;     // an new contact is being added therefore is not saved
                list.clear();       // clear then update of the ListView for feedback to the user
                list.add("Contact added: "+contact.toString());
                    // clearing fields for a new entry
                firstNameTextField.clear();
                lastNameTextField.clear();
                idTextField.clear();
                phoneNumberTextField.clear();
            }
            else {                // contact has not been added
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Contact can not be added, it is already in your list.");
                error.show();
            }
        });
        // listener to remove a contact, if it exists(checked in the controller Class for separation
        // of concerns) with confirmation shown in ListView, display of error otherwise,
        // and reset of the TextFields
        removeButton.setOnAction(e -> {
            contact.setFirstName(firstNameTextField.getText());
            contact.setLastName(lastNameTextField.getText());
            contact.setId(idTextField.getText());
            contact.setPhoneNumber(phoneNumberTextField.getText());

            if (controller.removeContact(contact)) {
                lastEntrySaved = false;     // an new contact is being added therefore is not saved
                list.clear();       // clear then update of the ListView for feedback to the user
                list.add("Contact removed: "+contact.toString());
                // clearing fields for a new entry
                firstNameTextField.clear();
                lastNameTextField.clear();
                idTextField.clear();
                phoneNumberTextField.clear();
            }
            else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Contact is not the list.");
                error.show();
            }
        });
        // display of the list of contacts in the ListView through the ObservableList(list),
        // from the controller
        listButton.setOnAction(e -> {
            list.clear();
            controller.getList(list);
        });

        HBox thirdRow = new HBox(10,addButton, removeButton, listButton);
        thirdRow.setSpacing(5);
        thirdRow.setPadding(new Insets(10, 10, 10, 10));
        return thirdRow;
    }
    // fifthRow: borderPane with 2 HBox (1:load/save - 2:Exit)
    private BorderPane setupFileExit(ObservableList<String> list, Controller controller){
        Button loadButton = new Button("Load");
        Button saveButton = new Button("Save");
        Button exitButton = new Button("Exit");
        HBox leftHBox = new HBox(10,loadButton, saveButton);
        leftHBox.setSpacing(5);
        HBox rightHBox = new HBox(10,exitButton);
        rightHBox.setSpacing(5);

        loadButton.setOnAction(e -> {
            boolean success = controller.loadList();
            if (!success){
                list.clear();       // clear then update of the ListView for feedback to the user
                list.add("Error: the list of contacts couldn't be loaded");
            }
            else {
                list.clear();       // clear then update of the ListView for feedback to the user
                list.add("The list of contacts has been loaded");
            }
        });

        saveButton.setOnAction(e ->{
            boolean success = controller.saveList();
            if (!success){
                list.clear();       // clear then update of the ListView for feedback to the user
                list.add("Error: the list of contacts couldn't be saved");
            }
            else {
                list.clear();       // clear then update of the ListView for feedback to the user
                list.add("The list of contacts has been saved");
            }
        });

        // exit listener with check for saved contacts
        exitButton.setOnAction(e -> {
            if (lastEntrySaved) {   // no contact has been added or removed since the last save:
                Alert exit = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
                exit.showAndWait()
                        .filter(choice -> choice == ButtonType.OK)
                        .ifPresent(choice -> {
                            boolean success = controller.saveList();
                            if (!success){
                                list.clear();       // clear then update of the ListView for feedback to the user
                                list.add("Error: the list of contacts couldn't be saved");
                            }
                            else {
                                list.clear();       // clear then update of the ListView for feedback to the user
                                list.add("The list of contacts has been saved");
                            }
                            System.exit(0);
                        });
            }
            else {     // one (or more) contact has been added or removed since the last save:
                Alert exit = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save the list of contacts?");
                exit.showAndWait()
                        .filter(choice -> choice == ButtonType.OK)
                        .ifPresent(choice -> System.exit(0));
            }
        });

        BorderPane.setAlignment(leftHBox,Pos.CENTER_LEFT);
        BorderPane.setAlignment(rightHBox,Pos.CENTER_RIGHT);
        BorderPane fifthRow = new BorderPane();
        fifthRow.setLeft(leftHBox);
        fifthRow.setRight(rightHBox);
        fifthRow.setPadding(new Insets(10, 10, 10, 10));
        return fifthRow;
    }




    // for personal use and future reference:
    //    LocalDateTime dateTime = LocalDateTime.now(); // Gets the current date and time
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    //
    //    [String: dateTime.format(formatter)]
}
