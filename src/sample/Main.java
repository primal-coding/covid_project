package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller controller = new Controller();
        primaryStage.setTitle("Close contacts covid tracker");
        MainView mainView = new MainView();
        Scene mainScene = mainView.initScene(this, controller);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
