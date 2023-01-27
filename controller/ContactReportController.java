package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import utility.AppointmentDBA;
import utility.ContactDBA;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactReportController implements Initializable {
    public Button backButton;
    public ComboBox contactBox;
    public TableColumn idCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIDCol;
    public TableView contactSchedule;

    ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments = AppointmentDBA.getAllAppointments();

    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportFormView.fxml"));
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method Initializes the combo box with contacts and the schedule view with their appointment schedule.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactBox.setItems(ContactDBA.getAllContacts());

        contactSchedule.setItems(contactAppointments);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startString"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endString"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

    }

    /**
     * This method populates the contact appointment list with the appointments corresponding to the to the contact selected.
     * @param actionEvent A contact was selected from the combo box.
     */
    public void onContactSelected(ActionEvent actionEvent) {

        contactAppointments.clear();

        Contact currentContact = (Contact) contactBox.getSelectionModel().getSelectedItem();

        for (Appointment appointment : allAppointments){
            if (appointment.getContact_id() == currentContact.getId()){
                contactAppointments.add(appointment);
            }
        }
    }
}
