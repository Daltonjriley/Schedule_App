package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;
import utility.DBConnection;

import java.time.LocalDateTime;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginFormView.fxml"));
        primaryStage.setTitle("Schedule Application");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr","FR"));
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }


}
