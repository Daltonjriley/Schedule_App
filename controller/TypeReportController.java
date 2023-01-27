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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import utility.AppointmentDBA;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TypeReportController implements Initializable {
    public Button backButton;
    public ComboBox typeBox;
    public ComboBox monthBox;
    public Label appointmentAmount;
    public Button showButton;
    private ObservableList<Appointment> allAppointments = AppointmentDBA.getAllAppointments();
    private ObservableList<String> typeList = FXCollections.observableArrayList();
    private ObservableList<Appointment> filteredList = FXCollections.observableArrayList();

    /**
     * This method initializes the combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Appointment appointment : allAppointments) {
            String newType = appointment.getType();
            if(!typeList.contains(newType)){
                typeList.add(newType);
            }
        }
        typeBox.setItems(typeList);

        monthBox.getItems().add("JANUARY");
        monthBox.getItems().add("FEBRUARY");
        monthBox.getItems().add("MARCH");
        monthBox.getItems().add("APRIL");
        monthBox.getItems().add("MAY");
        monthBox.getItems().add("JUNE");
        monthBox.getItems().add("JULY");
        monthBox.getItems().add("AUGUST");
        monthBox.getItems().add("SEPTEMBER");
        monthBox.getItems().add("OCTOBER");
        monthBox.getItems().add("NOVEMBER");
        monthBox.getItems().add("DECEMBER");

    }

    /**
     *This method contains a lambda expression.
     * Iterates through all appointments and returns the appoints that have the same type and month selected.
     * Then the lambda converts the size of that list into a string to be displayed.
     * @param actionEvent
     */
    public void onShowButton(ActionEvent actionEvent) {
        filteredList.clear();
        appointmentAmount.setText("");
        for(Appointment appointment : allAppointments){
            if (appointment.getType().equals(typeBox.getSelectionModel().getSelectedItem()) &&
                    appointment.getStart().getMonth().toString().equals(monthBox.getSelectionModel().getSelectedItem())){
                filteredList.add(appointment);
            }
        }

        //lambda expression that converts int into string
        intToString listSize = () -> String.valueOf(filteredList.size());
        appointmentAmount.setText(listSize.sizeToString());
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportFormView.fxml"));
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    interface intToString{
        String sizeToString();
    }
}
