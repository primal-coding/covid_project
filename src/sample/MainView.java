package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainView {

    LocalDateTime dateTime = LocalDateTime.now(); // Gets the current date and time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    TextField firstNameTextField = new TextField(" ");
    TextField lastNameTextField = new TextField(" ");
    TextField idTextField = new TextField(" ");
    TextField phoneNumberTextField = new TextField(" ");
    Boolean isListValid = false;
    Boolean lastEntrySaved = true;  // as a contact been added but not saved?

    public Scene initScene(Main main, Controller controller){
        // Contact contact = new Contact();    // used to add/remove
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
        // 2nd row: FlowPane (for the 4 entries)
        FlowPane secondRow = new FlowPane();
        secondRow = setupTextEntries();             // calling a method for clarity of reading
        // 3rd row: HBox (for the add/remove/list buttons)
        HBox thirdRow = new HBox();
        thirdRow = setupPrimaryButtons(list, controller);
        // top of BorderPane
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(firstRow,secondRow,thirdRow);

        // 4th row: List - center of BorderPane
        ListView<String> fourthRow = new ListView<String>();
//        list = FXCollections.observableArrayList (
//                "People", "in", "the", "Application");
//        items.addListener(e -> {
//
//        });
        list.addListener( (ListChangeListener<String>) change -> fourthRow.setItems(list));
        fourthRow.setItems(list);

        // A SUIVRE

        // 5th row: borderPane with 2 HBox (with file I/O and Exit buttons)
        BorderPane fifthRow = setupFileExit();

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

    private HBox setupPrimaryButtons(ObservableList<String> list, Controller controller){
        Button addButton = new Button("ADD");
        Button removeButton = new Button("Remove");
        Button listButton = new Button("List");
        Contact contact = new Contact();

        ObservableList<String> temp = FXCollections.observableArrayList() ;
        addButton.setOnAction(e -> {
            showMeContact(contact);
            contact.setFirstName(firstNameTextField.getText());
            contact.setLastName(lastNameTextField.getText());
            contact.setId(idTextField.getText());
            contact.setPhoneNumber(phoneNumberTextField.getText());
            if (controller.addContact(contact)){
                lastEntrySaved = false;     // an new contact is being added therefore is not saved
                list.clear();
                list.add("["+dateTime.format(formatter)+"] Contact added: "+contact.toString());

                firstNameTextField.clear();
                lastNameTextField.clear();
                idTextField.clear();
                phoneNumberTextField.clear();
            }
            else {                // cant add
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Contact already exist.");
                error.show();
            }
        });

        removeButton.setOnAction(e -> {

            // items.remove
//            Alert error = new Alert(Alert.AlertType.ERROR);
//            error.setContentText("Contact is not the list.");
//            error.show();
            // else {
            //      lastEntrySaved = false; // the list has changed but has not been saved
            // }
        });
        // A SUIVRE
        listButton.setOnAction(e -> {
            list.clear();
            controller.getList(list);

        });

        HBox thirdRow = new HBox(10,addButton, removeButton, listButton);
        thirdRow.setSpacing(5);
        thirdRow.setPadding(new Insets(10, 10, 10, 10));
        return thirdRow;
    }

    private BorderPane setupFileExit(){
        Button loadButton = new Button("Load");
        Button saveButton = new Button("Save");
        Button exitButton = new Button("Exit");
        HBox leftHBox = new HBox(10,loadButton, saveButton);
        leftHBox.setSpacing(5);
        HBox rightHBox = new HBox(10,exitButton);
        rightHBox.setSpacing(5);

        // A SUIVRE
        // Save first? => lastEntrySaved == true;
        exitButton.setOnAction(e -> {
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            exit.showAndWait()
                .filter(choice -> choice == ButtonType.OK)
                .ifPresent(choice -> System.exit(0));
        });
        // A SUIVRE

        BorderPane.setAlignment(leftHBox,Pos.CENTER_LEFT);
        BorderPane.setAlignment(rightHBox,Pos.CENTER_RIGHT);
        BorderPane fifthRow = new BorderPane();
        fifthRow.setLeft(leftHBox);
        fifthRow.setRight(rightHBox);
        fifthRow.setPadding(new Insets(10, 10, 10, 10));
        return fifthRow;
    }

    void showMeItems(ObservableList<String> items){
        for (Object o : items){
            System.out.println("sMI "+ o.toString());
        }
    }

    void showMeContact(Contact c){
        System.out.println("sMC "+c.toString());
    }
}
