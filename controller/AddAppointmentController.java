package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import utility.AppointmentDBA;
import utility.ContactDBA;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Provides functionality to the add appointment screen.
 */
public class AddAppointmentController implements Initializable {
    public ComboBox contactBox;
    public TextField idField;
    public TextField titleField;
    public TextField descField;
    public TextField typeField;
    public TextField cIDField;
    public TextField uIDField;
    public TextField locationField;
    public Button saveButton;
    public Button cancelButton;
    public DatePicker startPicker;
    public DatePicker endPicker;
    public ComboBox startTimePicker;
    public ComboBox endTimePicker;

    public void onSave(ActionEvent actionEvent) throws SQLException, IOException {
        int id = 0;
        String title = titleField.getText();
        String description = descField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        int customerID = Integer.parseInt(cIDField.getText());
        int userID = Integer.parseInt(uIDField.getText());
        Contact contact = (Contact) contactBox.getSelectionModel().getSelectedItem();
        int contactID = contact.getId();
        LocalDate startDate = startPicker.getValue();
        LocalDate endDate = endPicker.getValue();
        LocalTime startTime = (LocalTime) startTimePicker.getValue();
        LocalTime endTime = (LocalTime) endTimePicker.getValue();

        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        ObservableList<Appointment> customerAppointments = AppointmentDBA.getCustomerAppointments(customerID);



        if(startTime.isBefore(LocalTime.of(8,0)) || endTime.isAfter(LocalTime.of(22,0))) {
            utility.Alerts.error("Error", "Appointment is outside of business hours.", "Please select a time between 8:00 AM and 10:00 PM EST.");

        }
        else{

            for(Appointment appointment : customerAppointments){
                int checkID = appointment.getCustomer_id();
                LocalDateTime checkStart = appointment.getStart();
                LocalDateTime checkEnd = appointment.getEnd();
                if(customerID == checkID &&
                        (start.isEqual(checkStart) || start.isAfter(checkStart)) && start.isBefore(checkEnd) ||
                        end.isAfter(checkStart) && (end.isEqual(checkEnd) || end.isBefore(checkEnd)) ||
                        (start.isEqual(checkStart)) || start.isBefore(checkStart) && (end.isEqual(checkEnd) || end.isAfter(checkEnd))){
                    utility.Alerts.error("Error", "This appointment conflicts with an existing appointment.","Please change Appointment window.");
                    return;
                }
            }

            Appointment newAppointment = new Appointment(id, title, description, location, type, start, end, customerID, userID, contactID);
            AppointmentDBA.addAppointment(newAppointment);


            Parent root = FXMLLoader.load(getClass().getResource("/view/MainFormView.fxml"));
            Stage primaryStage = (Stage) saveButton.getScene().getWindow();
            primaryStage.setTitle("Scheduling Application");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    /**
     * This method redirects the user to the main screen when they press the cancel button.
     * @param actionEvent The cancel button is pressed.
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainFormView.fxml"));
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactBox.setItems(ContactDBA.getAllContacts());

        LocalTime comboStart = LocalTime.of(7,0);
        LocalTime comboEnd = LocalTime.of(23,0);

        while(comboStart.isBefore(comboEnd.plusSeconds(1))){
            startTimePicker.getItems().add(comboStart);
            endTimePicker.getItems().add(comboStart);
            comboStart = comboStart.plusMinutes(30);
        }




    }
}
