package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Appointment;
import utility.AppointmentDBA;

import java.io.IOException;

/**
 * This class provides functionality to the reports screen.
 */
public class ReportsController {
    public Button backButton;
    public Button typeReportButton;
    public Button contactReportButton;
    public Button userReportButton;

    /**
     * This method takes the user back to the main screen.
     * @param actionEvent The back button is pressed.
     * @throws IOException
     */
    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainFormView.fxml"));
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onTypeReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/TypeReportFormView.fxml"));
        Stage primaryStage = (Stage) typeReportButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onContactReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactReportFormView.fxml"));
        Stage primaryStage = (Stage) contactReportButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onUserReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserReportFormView.fxml"));
        Stage primaryStage = (Stage) userReportButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
