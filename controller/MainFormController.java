package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utility.Alerts;
import utility.AppointmentDBA;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides functionality to the main screen.
 */
public class MainFormController implements Initializable {
    public TableView appScheduleView;
    public TableColumn idCol;
    public TableColumn titleCol;
    public TableColumn descCol;
    public TableColumn locCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIDCol;
    public TableColumn userIDCol;
    public RadioButton weekRadio;
    public ToggleGroup timeFrame;
    public RadioButton monthRadio;
    public Button modAppButton;
    public Button addAppButton;
    public Button delAppButton;
    public Button customerButton;
    public Button reportButton;
    public RadioButton allRadio;

    private LocalDateTime currentTime = LocalDateTime.now();

    private ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();

    private ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

    /**
     * This method sets the appointment table view with appointments in the current week.
     * @param actionEvent The week radio tab was selected.
     */
    public void onWeekRadio(ActionEvent actionEvent) {
        LocalDateTime startOfWeek = currentTime.minusDays(currentTime.getDayOfWeek().getValue());
        LocalDateTime endOfWeek = startOfWeek.plusDays(6);
        for (Appointment appointment : AppointmentDBA.getAllAppointments()){
            if(appointment.getStart().isBefore(endOfWeek) && appointment.getStart().isAfter(startOfWeek)){
                weekAppointments.add(appointment);
            }
        }
        appScheduleView.setItems(weekAppointments);
    }

    /**
     * This method sets the appointment table view with appointments in the current month.
     * @param actionEvent The month radio tab was selected.
     */
    public void onMonthRadio(ActionEvent actionEvent) {
        for(Appointment appointment : AppointmentDBA.getAllAppointments()){
            if(appointment.getStart().getMonth() == currentTime.getMonth()){
                monthAppointments.add(appointment);
            }
        }
        appScheduleView.setItems(monthAppointments);
    }

    /**
     * This method takes the user to the main customer screen.
     * @param actionEvent The manage customers button is pressed.
     * @throws IOException
     */
    public void onCustomerButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerFormView.fxml"));
        Stage primaryStage = (Stage) customerButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method takes the user to the reports screen.
     * @param actionEvent The reports button is pressed.
     * @throws IOException
     */
    public void onReportButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportFormView.fxml"));
        Stage primaryStage = (Stage) reportButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method deletes a specified appointment and displays a confirmation and message with the appointment information.
     * @param actionEvent This is the delete button being pressed.
     * @throws SQLException
     */
    public void onDeleteApp(ActionEvent actionEvent) throws SQLException {
        Appointment deleteAppointment = (Appointment) appScheduleView.getSelectionModel().getSelectedItem();
        int appId = deleteAppointment.getId();
        String appType = deleteAppointment.getType();
        boolean deleted = false;
        if(deleteAppointment == null){
            utility.Alerts.error("Error","No appointment selected.", "Please select an appointment.");
        }else{
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("You are about to delete an appointment.");
            confirm.setHeaderText("Delete Appointment?");
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                AppointmentDBA.deleteAppointment(deleteAppointment);
                deleted = true;

                appScheduleView.setItems(AppointmentDBA.getAllAppointments());

//                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
//                descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
//                locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
//                contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_id"));
//                typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
//                startCol.setCellValueFactory(new PropertyValueFactory<>("startString"));
//                endCol.setCellValueFactory(new PropertyValueFactory<>("endString"));
//                customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
//                userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
            }
            if (deleted){
                Alerts.appointmentDeleted(appId, appType);
            }
        }
    }

    /**
     * This method takes the user to the modify appointment screen.
     * @param actionEvent The modify appointment button is pressed.
     * @throws IOException
     */
    public void onModApp(ActionEvent actionEvent) throws IOException {
        ModifyAppointmentController.setAppointment((Appointment) appScheduleView.getSelectionModel().getSelectedItem());

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointmentFormView.fxml"));
        Stage primaryStage = (Stage) modAppButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method takes the user to the add appointment screen.
     * @param actionEvent The add appointment button is pressed.
     * @throws IOException
     */
    public void onAddApp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentFormView.fxml"));
        Stage primaryStage = (Stage) addAppButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method initializes the calender with appointment objects.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appScheduleView.setItems(AppointmentDBA.getAllAppointments());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_id"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startString"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endString"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        boolean appointmentFound = false;

        for (Appointment appointment : AppointmentDBA.getAllAppointments()){

            if (currentTime.isBefore(appointment.getStart()) && currentTime.isAfter(appointment.getStart().minusMinutes(15))){
                appointmentFound = true;
                utility.Alerts.upcomingAppointment(appointment);
            }
        }
        if (!appointmentFound){
            utility.Alerts.noUpcomingAppointment();
        }


    }

    /**
     * This method sets the schedule table view with all appointments.
     * @param actionEvent The all radio tab was selected.
     */
    public void onAllRadio(ActionEvent actionEvent) {
        appScheduleView.setItems(AppointmentDBA.getAllAppointments());
    }

    /**
     * This method exits the application.
     * @param actionEvent The exit button was clicked.
     */
    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
