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

public class MainView {

    TextField firstNameTextField = new TextField(" ");
    TextField lastNameTextField = new TextField(" ");
    TextField idTextField = new TextField(" ");
    TextField phoneNumberTextField = new TextField(" ");
    Boolean isListValid = false;

    public Scene initScene(Main main, Controller controller){
        Contact contact = new Contact();
        ObservableList<String> items = FXCollections.observableArrayList (
                "People", "in", "the", "Application");


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
        thirdRow = setupPrimaryButtons(contact, items, controller);
        // top of BorderPane
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(firstRow,secondRow,thirdRow);

        // 4th row: List - center of BorderPane
        ListView<String> fourthRow = new ListView<String>();
        items = FXCollections.observableArrayList (
                "People", "in", "the", "Application");
//        items.addListener(e -> {
//
//        });
        fourthRow.setItems(items);

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

    private HBox setupPrimaryButtons(Contact contact, ObservableList<String> items, Controller controller){
        Button addButton = new Button("ADD");
        Button removeButton = new Button("Remove");
        Button listButton = new Button("List");
        ObservableList<String> temp = FXCollections.observableArrayList() ;
        addButton.setOnAction(e -> {
            if (controller.contactExist(contact.getId())){
                // cant add
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Contact already exist.");
                error.show();
            }
            else {
                if (isListValid){
                    items.removeAll();
                    controller.getList(items);
                }
                contact.setFirstName(firstNameTextField.getText());
                contact.setLastName(lastNameTextField.getText());
                contact.setId(idTextField.getText());
                contact.setPhoneNumber(phoneNumberTextField.getText());
                firstNameTextField.clear();
                lastNameTextField.clear();
                idTextField.clear();
                phoneNumberTextField.clear();

                items.add(contact.toString());
                showMeItems(items);
                controller.addContact(contact);
            }
        });
        removeButton.setOnAction(e -> {
            // items.remove
//            Alert error = new Alert(Alert.AlertType.ERROR);
//            error.setContentText("Contact is not the list.");
//            error.show();
        });
        // A SUIVRE

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
            System.out.println(o.toString());
        }
    }
}
